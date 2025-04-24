package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.Model.Investor;
import com.example.demo.Repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class InvestoreService {

    private final InvestorRepository investorRepository;


    public void addInvestore(Investor investor){
        if (investorRepository.existsByEmail(investor.getEmail())){
            throw new ApiException("this email already exists in the system");
        }
        investorRepository.save(investor);
    }

    public List getAllInvestor(){
        return investorRepository.findAll();
    }

    public void updateInvesitor(Investor investor, Integer id){
        Investor oldInvestor= investorRepository.getInvestoreById(id);
        if (oldInvestor==null){
            throw new ApiException("investor not found");
        }
        oldInvestor.setName(investor.getName());
        oldInvestor.setEmail(investor.getEmail());
        oldInvestor.setPassword(investor.getPassword());
        oldInvestor.setPhone_number(investor.getPhone_number());
    }


    public void deleteInvestor(Integer id){
        Investor oldInvestor= investorRepository.getInvestoreById(id);
        if (oldInvestor==null){
            throw new ApiException("investor not found");
        }
        investorRepository.delete(oldInvestor);
    }


    //--------------------------------------
}
