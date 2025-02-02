package com.yourpackage.budgetapp.controller;

import com.yourpackage.budgetapp.model.Budget;
import com.yourpackage.budgetapp.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetController(BudgetRepository budgetRepository){
        this.budgetRepository = budgetRepository;
    }

    
    @GetMapping
    public List<Budget> getAllBudgets(){
        return budgetRepository.findAll();
    }

    @PostMapping
    public Budget createBudget(@RequestBody Budget budget){
        return budgetRepository.save(budget);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
    return budgetRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }


    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget updateBudget){
        return budgetRepository.findById(id)
            .map(budget -> {
                budget.setName(updateBudget.getName());
                budget.setAmount(updateBudget.getAmount());
                budget.setCategory(updateBudget.getCategory());
                return budgetRepository.save(budget);
        })
        .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id){
        budgetRepository.deleteById(id);
    }
}
