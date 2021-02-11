package com.example.shopcatalog.utils;

import com.example.shopcatalog.common.Constants;

public class FullUrl {

    public static String getFullUrl(String category, String productUrl) {

        String baseUrl = Constants.CATALOG_API_BASE_URL;
        String categoryUrl;

        switch (category) {
            case "tv":
                categoryUrl = Constants.CATALOG_API_URL_TV_CATEGORY;
                break;
            case "phone":
                categoryUrl = Constants.CATALOG_API_URL_PHONE_CATEGORY;
                break;
            case "watch":
                categoryUrl = Constants.CATALOG_API_URL_WATCH_CATEGORY;
                break;
            case "fridge":
                categoryUrl = Constants.CATALOG_API_URL_FRIDGE_CATEGORY;
                break;
            case "furniture":
                categoryUrl = Constants.CATALOG_API_URL_FURNITURE_CATEGORY;
                break;
            default:
                return baseUrl;
        }
        return baseUrl + categoryUrl + productUrl;
    }
}
