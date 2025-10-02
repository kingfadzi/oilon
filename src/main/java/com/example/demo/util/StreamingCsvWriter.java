package com.example.demo.util;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Iterator;

public class StreamingCsvWriter {

    public static <T> StreamingResponseBody streamCsv(Iterator<T> dataIterator, Class<T> clazz) {
        return outputStream -> {
            try (Writer writer = new OutputStreamWriter(outputStream)) {
                Field[] fields = clazz.getDeclaredFields();

                // Write header
                writeHeader(writer, fields);

                // Write rows
                while (dataIterator.hasNext()) {
                    T item = dataIterator.next();
                    writeRow(writer, item, fields);
                }

                writer.flush();
            }
        };
    }

    private static void writeHeader(Writer writer, Field[] fields) throws IOException {
        for (int i = 0; i < fields.length; i++) {
            writer.write(escapeCsvValue(fields[i].getName()));
            if (i < fields.length - 1) {
                writer.write(",");
            }
        }
        writer.write("\n");
    }

    private static <T> void writeRow(Writer writer, T item, Field[] fields) throws IOException {
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(item);
                writer.write(escapeCsvValue(value != null ? value.toString() : ""));
            } catch (IllegalAccessException e) {
                writer.write("");
            }
            if (i < fields.length - 1) {
                writer.write(",");
            }
        }
        writer.write("\n");
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
