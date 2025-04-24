package com.example.demo.Controller;

import com.example.demo.API.ApiResponse;
import com.example.demo.Model.Investor;
import com.example.demo.Service.InvestoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/investor")
@RequiredArgsConstructor
public class InvestorController {

    private final InvestoreService investoreService;




    @PostMapping("/add")
    public ResponseEntity add(@Valid @RequestBody Investor investor){
        investoreService.addInvestore(investor);
        return ResponseEntity.status(200).body(new ApiResponse("Added successfully"));
    }

    @GetMapping("/getall")
    public ResponseEntity getall(){
        return ResponseEntity.status(200).body(investoreService.getAllInvestor());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody Investor investor, @PathVariable Integer id){
        investoreService.updateInvesitor(investor,id);
        return ResponseEntity.status(200).body(new ApiResponse("Updated successfully "));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        investoreService.deleteInvestor(id);
        return ResponseEntity.status(200).body(new ApiResponse("Deleted successfully"));
    }

//----------------------------------------------------------------



}
