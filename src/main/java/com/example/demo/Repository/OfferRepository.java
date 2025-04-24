package com.example.demo.Repository;

import com.example.demo.Model.Investor;
import com.example.demo.Model.Offer;
import com.example.demo.Model.Owner;
import com.example.demo.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer> {
    int countByInvestorIdAndOfferStatus(Integer investorId, String offerStatus);  // تعديل هذا السطر

    int countByOwnerId(int ownerId);

    int countByOwnerIdAndOfferStatus(int ownerId, String accepted);

    List<Offer> findOfferByIdAndOfferStatusNot(Integer id, String accepted);

    Offer findOfferById(Integer offerId);


    List<Offer> findByPropertyId(Integer propertyId);
    public interface offerRepository extends JpaRepository<Offer, Integer> {
        Offer findTopByInvestorIdOrderByLastOfferTimeDesc(Integer investorId);
    }
        //Duja  استعلام للحصول على آخر عرض للمستثمر
        Offer findTopByInvestorIdOrderByLastOfferTimeDesc(Integer investorId);




    List<Offer> findByProperty_IdAndOfferStatusNot(Integer propertyId, String offerStatus);

    // Get the highest offer cost
    @Query("SELECT o FROM Offer o ORDER BY o.cost DESC")
    List<Offer> findTopOfferByCost();
    // Get the least offer years
    @Query("SELECT o FROM Offer o ORDER BY o.years ASC")
    List<Offer> findTopOfferByYears();

    Offer findTopByOrderByCostDesc();
    Offer findTopByOrderByYearsAsc();

    @Query("SELECT o.property.id, COUNT(o) FROM Offer o GROUP BY o.property.id")
    List<Object[]> countOffersPerProperty();

    // Used @Param to bind method parameter to named JPQL query parameter
    @Query("SELECT COUNT(o) FROM Offer o WHERE o.property.id = :propertyId")
    Integer countOffersByPropertyId(@Param("propertyId") Integer propertyId);

    // Method to count offers by owner
    Integer countByOwner(Owner owner);

    //Albatool
    //bestOffer
    List<Offer> findByPropertyAndOfferStatus(Property property, String offerStatus);

    //Albatool
    //افضل العقارات لمستثمر
    List<Offer>findByInvestor(Investor investor);


}