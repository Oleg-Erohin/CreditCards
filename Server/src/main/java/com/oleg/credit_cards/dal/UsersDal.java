package com.oleg.credit_cards.dal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleg.credit_cards.consts.Consts;
import com.oleg.credit_cards.dto.SuccessfulLoginDetails;
import com.oleg.credit_cards.dto.User;
import com.oleg.credit_cards.enums.ErrorType;
import com.oleg.credit_cards.exceptions.ApplicationException;
import com.oleg.credit_cards.utils.CommonFunctions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersDal {
    public SuccessfulLoginDetails login(int id) throws ApplicationException {
        try{
            String filePath = Consts.usersJsonFilePath;
            String jsonString = CommonFunctions.getJsonStringByFilePath(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            List<User> users = objectMapper.readValue(jsonString, new TypeReference<List<User>>() {});
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getId() == id) {
                    SuccessfulLoginDetails successfulLoginDetails = new SuccessfulLoginDetails();
                    successfulLoginDetails.setUserName(user.getUsername());
                    return successfulLoginDetails;
                }
            }
            return null;
        } catch (Exception e){
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Could not get user", e);
        }
    }

    public User getByUsername(String username) throws ApplicationException {
        try{
            String filePath = Consts.usersJsonFilePath;
            String jsonString = CommonFunctions.getJsonStringByFilePath(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            List<User> users = objectMapper.readValue(jsonString, new TypeReference<List<User>>() {});
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
            return null;
        } catch (Exception e){
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Could not get user", e);
        }
    }
}
