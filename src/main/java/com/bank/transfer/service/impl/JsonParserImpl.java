package com.bank.transfer.service.impl;

import com.bank.transfer.service.JsonParser;
import com.google.gson.Gson;

/**
 * @author Ehtiram_Abdullayev on 2/11/2020
 * @project bank-transfer
 */
public class JsonParserImpl implements JsonParser {
    @Override
    public <T> T fromJSonToPOJO(String json, Class<T> classType) {
        return new Gson().fromJson(json, classType);
    }

    @Override
    public String toJsonPOJO(Object data) {
        return new Gson().toJson(data);
    }
}
