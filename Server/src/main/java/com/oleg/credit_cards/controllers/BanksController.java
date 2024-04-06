package com.oleg.credit_cards.controllers;

import com.oleg.credit_cards.dto.Bank;
import com.oleg.credit_cards.exceptions.ApplicationException;
import com.oleg.credit_cards.logic.BanksLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banks")
public class BanksController {
    private BanksLogic banksLogic;

    @Autowired
    public BanksController(BanksLogic banksLogic) {
        this.banksLogic = banksLogic;
    }

    @GetMapping
    public List<Bank> getBanks() throws ApplicationException {
        return this.banksLogic.getBanks();
    }
}
