package br.com.support.car_dealer_api.util;

import java.util.UUID;

public class UUIDUtil {

    public static String newUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean isValidUUID(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
