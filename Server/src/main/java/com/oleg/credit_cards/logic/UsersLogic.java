package com.oleg.credit_cards.logic;

import com.oleg.credit_cards.dal.UsersDal;
import com.oleg.credit_cards.dto.SuccessfulLoginDetails;
import com.oleg.credit_cards.dto.User;
import com.oleg.credit_cards.dto.UserLoginData;
import com.oleg.credit_cards.enums.ErrorType;
import com.oleg.credit_cards.exceptions.ApplicationException;
import com.oleg.credit_cards.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersLogic {
    private UsersDal usersDal;

    @Autowired
    public UsersLogic(UsersDal usersDal) {
        this.usersDal = usersDal;
    }

    public String login(UserLoginData userLoginData) throws ApplicationException {
        authenticatePassword(userLoginData);
        User user = this.usersDal.getByUsername(userLoginData.getUsername());
        SuccessfulLoginDetails successfulLoginDetails = this.usersDal.login(user.getId());
        if (successfulLoginDetails == null) {
            throw new ApplicationException(ErrorType.LOGIN_FAILED);
        }
        try {
            String token = JWTUtils.createJWT(successfulLoginDetails);
            return token;
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to login", e);
        }
    }

    private void authenticatePassword(UserLoginData userLoginData) throws ApplicationException {
        User user = this.usersDal.getByUsername(userLoginData.getUsername());

        if(user == null){
            throw new ApplicationException(ErrorType.USER_NOT_FOUND);
        }

        if (user.getUsername().equals(userLoginData.getUsername())){
            if (user.getPassword().equals(userLoginData.getPassword())){
                return;
            }
        }
        throw new ApplicationException(ErrorType.LOGIN_FAILED);
    }
}
