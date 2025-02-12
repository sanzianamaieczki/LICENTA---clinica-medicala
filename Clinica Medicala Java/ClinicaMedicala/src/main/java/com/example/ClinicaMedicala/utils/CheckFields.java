package com.example.ClinicaMedicala.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CheckFields {

    //aceste campuri nu trebuie adaugate de utilizator
    private static final Set<String> DEFAULT_EXCLUDED_FIELDS  = Set.of("created_at", "updated_at", "is_deleted");

    //se foloseste pentru a verifica daca se adauga campuri goale
    public static String checkEmptyFields(Map<String, Object> emptyFields, Set<String> excludedFields) {
        StringBuilder errors = new StringBuilder();

        //pentru fiecare entitate in parte putem exclude anumite campuri (ex: id-urile entitatilor)
        Set<String> finalExcludedFields = new HashSet<>(DEFAULT_EXCLUDED_FIELDS);
        if (excludedFields != null) {
            finalExcludedFields.addAll(excludedFields);
        }

        emptyFields.forEach((field, value) ->{
            if(!finalExcludedFields.contains(field) &&
                    ( value == null || (value instanceof String && ((String) value).isEmpty()))) {
                errors.append("Campul: ").append(field).append(" nu poate fi null sau gol.").append(System.lineSeparator());
            }
        });
        return errors.toString().trim();
    }
}
