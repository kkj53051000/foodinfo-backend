package com.kp.foodinfo.util;

import java.text.DecimalFormat;

public class FormatUtil {

    public static String menuPriceFormat(int price) {
        DecimalFormat format = new DecimalFormat("###,###");

        String priceStr = format.format(price);

        return priceStr;
    }
}
