package com.example.ClinicaMedicala.utils;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^[0-9]{10}$");
    private static final Pattern CNP_REGEX = Pattern.compile("^[0-9]{13}$");
    private static final Pattern DATE_REGEX = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public static boolean validateEmail(String email) {
        return !EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return !PHONE_REGEX.matcher(phoneNumber).matches();
    }

    public static boolean validateCnp(String cnp) {
        return !CNP_REGEX.matcher(cnp).matches();
    }

    public static boolean validateDate(String date) {
        if(date == null || date.isEmpty() || !DATE_REGEX.matcher(date).matches()){
            return false;
        }

        String[] dateParts = date.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        int hour = Integer.parseInt(dateParts[3]);
        int minute = Integer.parseInt(dateParts[4]);

        if(day > YearMonth.of(year,month).lengthOfMonth()){
            return false;
        }

        LocalDateTime localDateTime = LocalDateTime.of(year,month,day,hour,minute);
        LocalDateTime now = LocalDateTime.now();

        return !now.isBefore(localDateTime);
    }
}
