package com.oleg.credit_cards.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CommonFunctions {
    public static String getJsonStringByFilePath(String filePath) throws IOException {
        StringBuilder jsonStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
        }
        String jsonString = jsonStringBuilder.toString();
        return jsonString;
    }

}
