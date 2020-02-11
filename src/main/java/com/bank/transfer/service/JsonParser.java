package com.bank.transfer.service;

/**
 * @author Ehtiram_Abdullayev on 2/11/2020
 * @project bank-transfer
 */
public interface JsonParser {
    <T> T fromJSonToPOJO(String jsonString, Class<T> classType);
    String toJsonPOJO(Object data);
}
