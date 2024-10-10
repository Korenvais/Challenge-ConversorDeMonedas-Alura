package utils;

import java.util.Map;

public class Validator {

    public static boolean nonNegativeNumber(Double inputNumber) {
        return inputNumber >= 0;
    }

    public static boolean validCode(String code, Map<String, String> nameCatalog) {
        return nameCatalog.get(code) != null;
    }

    public static boolean isDouble(String num){
        try {
            Double doubleValue = Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
