package com.example.demo.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class CsvUtil {

    public static <T> String convertToCsv(List<T> data) {
        if (data == null || data.isEmpty()) {
            return "";
        }

        StringBuilder csv = new StringBuilder();
        Class<?> clazz = data.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        // Build header
        csv.append(buildCsvHeader(fields));
        csv.append("\n");

        // Build rows
        for (T item : data) {
            csv.append(buildCsvRow(item, fields));
            csv.append("\n");
        }

        return csv.toString();
    }

    private static String buildCsvHeader(Field[] fields) {
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            header.append(escapeCsvValue(fields[i].getName()));
            if (i < fields.length - 1) {
                header.append(",");
            }
        }
        return header.toString();
    }

    private static <T> String buildCsvRow(T item, Field[] fields) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(item);
                row.append(escapeCsvValue(value != null ? value.toString() : ""));
            } catch (IllegalAccessException e) {
                row.append("");
            }
            if (i < fields.length - 1) {
                row.append(",");
            }
        }
        return row.toString();
    }

    private static String escapeCsvValue(String value) {
        if (value == null) {
            return "";
        }
        // Escape quotes and wrap in quotes if contains comma, quote, or newline
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }
}
