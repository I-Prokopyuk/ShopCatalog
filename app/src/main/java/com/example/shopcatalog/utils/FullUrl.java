package com.example.shopcatalog.utils;

import com.example.shopcatalog.common.Constants;

public class FullUrl {

    public static String getFullUrl(String category) {
        switch (category) {
            case "tv":
                break;
            default:
                return Constants.CATALOG_API_BASE_URL;
        }
    }
}
