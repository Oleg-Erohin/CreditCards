package com.oleg.credit_cards.controllers;

import com.oleg.credit_cards.dto.UserLoginData;
import com.oleg.credit_cards.exceptions.ApplicationException;
import com.oleg.credit_cards.logic.UsersLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    private UsersLogic usersLogic;

    @Autowired
    public UsersController(UsersLogic usersLogic) {
        this.usersLogic = usersLogic;
    }

    @PostMapping("/login")
    public String getBanks(@RequestBody UserLoginData userLoginData) throws ApplicationException {
        return this.usersLogic.login(userLoginData);
    }
}
