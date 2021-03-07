package com.nikhilcodes.expzen.core.service;

import com.nikhilcodes.expzen.core.repository.ExpenseRepository;
import com.nikhilcodes.expzen.core.repository.IncomeRepository;
import com.nikhilcodes.expzen.model.Income;
import com.nikhilcodes.expzen.shared.dto.IncomeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<IncomeDTO> getIncomeByUser(String uid) {
        List<IncomeDTO> incomeData = new ArrayList<>();

        this.incomeRepository.findAllByUserIdOrderByCreatedOnDescCreationTimestampDesc(uid).forEach(income -> {
            incomeData.add(
              new IncomeDTO(
                income.getValue(),
                income.getIncomeType(),
                income.getCreatedOn()
              )
            );
        });

        return incomeData;
    }

    public void addIncomeByUser(String uid, IncomeDTO incomeDTO) {
        Income income = new Income(incomeDTO);
        income.setUserId(uid);
        incomeRepository.saveAndFlush(income);
    }
}
