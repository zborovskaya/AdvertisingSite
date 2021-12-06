package by.zborovskaya.final_project.service.validation;

import by.zborovskaya.final_project.entity.ClientAdv;

public class ClientInfoValidator {

    private final static String EMAIL_PATTERN = "^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@" +
            "([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs" +
            "|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$";
    private final static String NAME_PATTERN =  "^[A-ZА-Я][a-zа-я]+$";
    private final static String PHONE_PATTERN = "^[0-9]{9}$";

    public static boolean isEmailValid(String email){
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean isFirstNameValid(String firstName){
        return firstName.matches(NAME_PATTERN);
    }

    public static boolean isLastNameValid(String lastName){
        return lastName.matches(NAME_PATTERN);
    }

    public static boolean isPhoneValid(String phone){
        return phone.matches(PHONE_PATTERN);
    }

}
