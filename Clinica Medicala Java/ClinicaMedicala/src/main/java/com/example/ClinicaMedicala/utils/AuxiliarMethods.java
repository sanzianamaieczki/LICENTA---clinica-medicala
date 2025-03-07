package com.example.ClinicaMedicala.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AuxiliarMethods {
    // creare automata a datei de nastere in functie de CNP-ul persoanei
    public static Date extractBirthDateFromNationalId(String national_id) {
        if(national_id == null || national_id.length() < 13){
            throw new IllegalArgumentException("CNP invalid");
        }

        char s = national_id.charAt(0); //sex-ul persoanei (M/F)
        int year = Integer.parseInt(national_id.substring(1,3));
        int month = Integer.parseInt(national_id.substring(3,5));
        int day = Integer.parseInt(national_id.substring(5,7));

        if(s== '1' || s== '2'){
            year = 1900 + year;
        }
        else if(s== '5' || s== '6'){
            year = 2000 + year;
        }
        else {
            throw new IllegalArgumentException("CNP invalid. Prefix necunoscut");
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(year,month-1,day,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
}
