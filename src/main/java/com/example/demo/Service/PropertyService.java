package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.DTO.InvestorPreferencesDTO;
import com.example.demo.DTO.PropertyRecommendationDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final OwnerRepository ownerRepository;
    private  final PropertyRepository propertyRepository;
    private OfferRepository offerRepository;
    private AdminRepository adminRepository;
    private EmailService emailService;
    private final ContractRepository contractRepository;

    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    public void addProperty(Property property){
        Owner owner = ownerRepository.findOwnerById(property.getId());
        if(owner == null){
            throw new ApiException("owner required to add property");
        }

        propertyRepository.save(property);
    }


    public void updateProperty(Integer id, Property property){
        Property oldProperty = propertyRepository.findPropertiesById(id);
        if(oldProperty == null){
            throw new ApiException("Property not found");
        }
        oldProperty.setAreaSize(property.getAreaSize());
        oldProperty.setDescription(property.getDescription());
        oldProperty.setLocation(property.getLocation());
        oldProperty.setType(property.getType());
        oldProperty.setTitle(property.getTitle());
        oldProperty.setSerialNumber(property.getSerialNumber());

        propertyRepository.save(oldProperty);
    }



    public void deleteProperty(Integer id){
        Property property = propertyRepository.findPropertiesById(id);
        if(property == null){
            throw new ApiException("Property not found");
        }

        propertyRepository.delete(property);
    }

    // Ohoud 4: if investor want to see the properties "allow to see the active properties only"
    public List<Property> getAllProperties(){
        List<Property> properties = propertyRepository.findAll();
        List<Property> activeProperties = new ArrayList<>();
        for (Property p: properties){
            if("Active".equals(p.getStatus())){
                activeProperties.add(p);
            }
        }

        return activeProperties;
    }



    //3 Duja
    public String calculateTotalAnnualProfitFromAllProperties(Integer ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ApiException("Owner not found"));

        List<Property> properties = propertyRepository.findByOwner(owner);
        if (properties.isEmpty()) {
            throw new ApiException("No properties found for this owner");
        }

        double totalAnnualProfit = properties.stream()
                .filter(p -> p.getAnnualRent() != null)
                .mapToDouble(Property::getAnnualRent)
                .sum();

        return "Total owner's profit from all properties during the year: " + totalAnnualProfit + "SR";
    }



    //4 Duja
    public boolean stopReceivingOffers(Integer propertyId) {
        return propertyRepository.findById(propertyId).map(property -> {
            property.setAcceptingOffers(false);
            propertyRepository.save(property);
            return true;
        }).orElse(false);
    }



    //Duja
    public List<Property> endingSoon(int proposedYears) {
        int proposedDays = proposedYears * 365;
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(proposedDays);
        return propertyRepository.findPropertiesByLeaseEndDateBetween(today, endDate);
    }

    public Property findById(Integer propertyId) {
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ApiException("Property not found"));
    }



    //Duja
    public double calculatePropertyPrice(Integer propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ApiException("Property not found"));

        double pricePerMeter = 1975.0;
        Double area = property.getAreaSize();

        if (area == null || area <= 0) {
            throw new ApiException("Invalid area size");
        }
        return area * pricePerMeter;
    }



    //6 بيحسب  متوسط العروض الي جته Duja
    public double calculateAverageOfferPrice(Integer propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ApiException("Property not found"));

        List<Offer> offers = offerRepository.findByPropertyId(propertyId);

        if (offers.isEmpty()) {
            return 0;
        }

        double totalPrice = 0;
        int validOffersCount = 0;

        for (Offer offer : offers) {
            Double cost = Double.valueOf(offer.getProposedCost());
            if (cost != null) {
                totalPrice += cost;
                validOffersCount++;
            }
        }

        return validOffersCount == 0 ? 0 : totalPrice / validOffersCount;
    }


    //حقت واحد حسبه متوسسط السعر لكل العروض Duja
    public Property getPropertyById(Integer propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        return property.orElse(null);
    }

    //Ohoud 5: owner can search by status to find the active or inactive property
    public List<Property> getPropertyByStatus(Integer ownerId,String status){
        return propertyRepository.findPropertyByStatus(status);
    }

    //Ohoud 6: owner can insert property
    public void addPropertyByOwner(Integer ownerId,Property property){
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if(owner == null){
            throw new ApiException("owner required to add property");
        }

        property.setOwner(owner);
        property.setStatus("pending");
        propertyRepository.save(property);

    }

    //Ohoud 8: only the admin can activate the properties and if it is active the owner well receive an email
    public void activeTheProperty(Integer propertyId,Integer adminId){
        Admin  admin = adminRepository.findAdminById(adminId);
        Property property =propertyRepository.findPropertiesById(propertyId);
        if(property == null){
            throw new ApiException("Property not found");
        }
        if(admin == null){
            throw new ApiException("Admin not found");
        }

        property.setStatus("Active");
        propertyRepository.save(property);

        String messageToOwner = "Your request for property " + property.getTitle() + " has been active.";
        emailService.sendEmail(property.getOwner().getEmail(), "Property is active now", messageToOwner);
    }

    //Ohoud 9: only admin could reject if owner not follow the instructions and receive an email
    public void rejectTheProperty(Integer propertyId,Integer adminId){
        Admin admin = adminRepository.findAdminById(adminId);
        Property property =propertyRepository.findPropertiesById(propertyId);
        if(property == null){
            throw new ApiException("Property not found");
        }
        if(admin == null){
            throw new ApiException("Admin not found");
        }

        property.setStatus("Inactive");
        propertyRepository.save(property);

        String messageToOwner = "Your request for property " + property.getTitle() + " rejected for failure to follow the instructions.";
        emailService.sendEmail(property.getOwner().getEmail(), "Property is Inactive now", messageToOwner);
    }
    //Taha.10
    public List<Property>  getPropertyByLocation(String location) {
        List<Property>  properties =  propertyRepository.findPropertiesByLocation(location);
        if (properties.isEmpty()) {
            throw new ApiException("No properties found in location: " + location);
        }
        return properties;
    }

    //Taha.11
    // Method to get properties that have no offers
    //Helps the user to market the property better
    public List<Property> getPropertiesWithNoOffers() {
        List<Property> allProperties = propertyRepository.findAll(); // Get all properties
        List<Property> propertiesWithNoOffers = new ArrayList<>();

        for (Property property : allProperties) {
            if (property.getOffer().isEmpty()) { // Check if the property has no offers
                propertiesWithNoOffers.add(property); // Add it to the list if it has no offers
            }
        }

        return propertiesWithNoOffers; // Return the filtered list
    }

    //albatool
    //تفضيلات المستثمر,استخراج العقارات والمواقع زالمساحات من العقود السابقه
    private InvestorPreferencesDTO getInvestorPreferences(Investor investor) {
        List<Contract> contracts = contractRepository.findByInvestor(investor);

        List<String> types = new ArrayList<>();
        List<String> locations = new ArrayList<>();
        List<Double> areas = new ArrayList<>();

        //المرور على كل عقد وجلب العقار الخاص به مع التاكد من عدم التكرار
        for (Contract contract : contracts) {
            Property property = contract.getProperty();
            if (!types.contains(property.getType())) types.add(property.getType());
            if (!locations.contains(property.getLocation())) locations.add(property.getLocation());
            if (!areas.contains(property.getAreaSize())) areas.add(property.getAreaSize());
        }
        return new InvestorPreferencesDTO(types, locations, areas);
    }
    //albstool
    //  جلب العقارات اللي المستثمر قدم عليها عروض سابقا لتجنب ترشيح نفس العقار
    private List<Integer> getOfferedPropertyIds(Investor investor) {
        List<Offer> offers = offerRepository.findByInvestor(investor);
        List<Integer> propertyIds = new ArrayList<>();//لحفظ ID العقارات اللي المستثمر قدم عليها عروض

        for (Offer offer : offers) {
            propertyIds.add(offer.getProperty().getId());//اضيف كل IDs العقارات في القائمه
        }
        return propertyIds;
    }

    //albatool
    //ترشيح العقارات المناسبه للمستثمر واستبعاد ماتم التقديم عليه
    private List<PropertyRecommendationDTO> filterRecommendedProperties(InvestorPreferencesDTO preferences, List<Integer>excludedProperties) {
        List<Property> allProperties = propertyRepository.findPropertyByStatus("Active");
        List<PropertyRecommendationDTO> result = new ArrayList<>();//قائمة العقارات المضله

        for (Property property : allProperties) {
            if (excludedProperties.contains(property.getId()))//تجنب العقار المُقدم عليه
                continue;

            //اذا نوع العقار موجود في قائمة الانواع المضله , ألخ اعتبره مناسب
            boolean matches = preferences.getPreferredTypes().contains(property.getType()) || preferences.getPreferredLocations().contains(property.getLocation())
                    || preferences.getPreferredAreas().contains(property.getAreaSize());

            if (matches) {
                result.add(new PropertyRecommendationDTO(property.getId(), property.getTitle(), property.getLocation(),
                        property.getType(), property.getAreaSize(), property.getStatus()));}}
        return result;
    }
    //albatool
    //الحصول على التوصيات المناسبه للمستثمر
    public List<PropertyRecommendationDTO> getRecommendedProperties(Integer id) {
        Investor investor=investorRepository.findInvestorById(id);
        if (investor == null) {
            throw new ApiException("Investor not found");
        }

        InvestorPreferencesDTO preferences = getInvestorPreferences(investor);
        List<Integer> excludedProperties = getOfferedPropertyIds(investor);
        return filterRecommendedProperties(preferences, excludedProperties);}//ارجاع


}





