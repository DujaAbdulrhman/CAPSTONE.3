package com.example.demo.Repository;

import com.example.demo.Model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.Contract;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {

    Contract findContractById(Integer id);
    Contract findContractByContractId(Integer contractId);

    //Albatool
    //افضل عقار للمستثمر
    List<Contract> findByInvestor(Investor investor);
}