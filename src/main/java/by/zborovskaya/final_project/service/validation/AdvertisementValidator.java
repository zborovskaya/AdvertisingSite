package by.zborovskaya.final_project.service.validation;

import by.zborovskaya.final_project.entity.Advertisement;

public class AdvertisementValidator {
    private final static String EMPTY_STRING = "";

    public static boolean isNotEmptyFields(Advertisement advertisement){
        return (advertisement.getCategory()!=null) &&
                (advertisement.getTitle()!=null) &&
                (advertisement.getText()!=null) &&
                (!EMPTY_STRING.equals(advertisement.getCategory())) &&
                (!EMPTY_STRING.equals(advertisement.getTitle())) &&
                (!EMPTY_STRING.equals(advertisement.getText()));
    }
}
