package smt.ort.houses.util;

import java.text.NumberFormat;

public class StringUtil {

    public static String formatCurrency(String currencyStr) {
        // Set price
        String formattedPrice = currencyStr;
        try {
            Float floatPrice = Float.parseFloat(formattedPrice);
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMinimumFractionDigits(0);
            formattedPrice = format.format(floatPrice);
        } catch (NumberFormatException e) {
            // swallow catch
        }

        return formattedPrice;
    }

    public static String formatSquareMeters(String squareMetersStr) {
        return squareMetersStr + " m\u00B2";
    }
}
