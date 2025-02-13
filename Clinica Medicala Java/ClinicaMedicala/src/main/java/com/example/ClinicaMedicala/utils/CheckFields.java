package com.example.ClinicaMedicala.utils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckFields {

    //aceste campuri nu trebuie adaugate de utilizator
    private static final Set<String> DEFAULT_EXCLUDED_FIELDS  = Set.of("created_at", "updated_at", "is_deleted");

    //se foloseste pentru a verifica daca se adauga campuri goale
    public static String checkEmptyFields(Map<String, Object> fields, Set<String> excludedFields) {
        if (fields == null || fields.isEmpty()) {
            return null;
        }
        //pentru fiecare entitate in parte putem exclude anumite campuri (ex: id-urile entitatilor)
        Set<String> finalExcludedFields = (excludedFields == null || excludedFields.isEmpty())
                ? DEFAULT_EXCLUDED_FIELDS
                : Stream.concat(DEFAULT_EXCLUDED_FIELDS.stream(), excludedFields.stream())
                .collect(Collectors.toUnmodifiableSet());

        String errors = fields.entrySet().stream()
                .filter(entry -> !finalExcludedFields.contains(entry.getKey()))
                .filter(entry -> isFieldEmpty(entry.getValue()))
                .map(entry -> "Campul: " + entry.getKey() + " nu poate fi null sau gol.")
                .collect(Collectors.joining(System.lineSeparator()));

        return errors.isEmpty() ? null : errors;
    }

    private static boolean isFieldEmpty(Object value) {
        return value == null || (value instanceof String && ((String) value).isBlank());
    }
}
