package com.example.ClinicaMedicala.utils;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^[0-9]{10}$");
    private static final Pattern CNP_REGEX = Pattern.compile("^[0-9]{13}$");

    public static boolean validateEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return PHONE_REGEX.matcher(phoneNumber).matches();
    }

    public static boolean validateCnp(String cnp) {
        return CNP_REGEX.matcher(cnp).matches();
    }
}
