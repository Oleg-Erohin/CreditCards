package com.oleg.credit_cards.logic;

import com.oleg.credit_cards.dal.BanksDal;
import com.oleg.credit_cards.dto.Bank;
import com.oleg.credit_cards.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanksLogic {
    private BanksDal banksDal;

    @Autowired
    public BanksLogic(BanksDal banksDal) {
        this.banksDal = banksDal;
    }

    @Cacheable(cacheNames = "banksCache", key = "#root.methodName")
    public List<Bank> getBanks() throws ApplicationException {
        return this.banksDal.getBanks();
    }
}
