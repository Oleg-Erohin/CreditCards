package com.oleg.credit_cards.dal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleg.credit_cards.consts.Consts;
import com.oleg.credit_cards.dto.Bank;
import com.oleg.credit_cards.enums.ErrorType;
import com.oleg.credit_cards.exceptions.ApplicationException;
import com.oleg.credit_cards.utils.CommonFunctions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BanksDal {
    public List<Bank> getBanks() throws ApplicationException {
        try {
            String filePath = Consts.banksJsonFilePath;
            String jsonString = CommonFunctions.getJsonStringByFilePath(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Bank> banks = objectMapper.readValue(jsonString, new TypeReference<List<Bank>>() {
            });
            return banks;
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Could not get banks", e);
        }
    }
}
