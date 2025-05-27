package ru.job4j.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

public final class GetFileContent {

    private final File file;

    public GetFileContent(File file) {
        this.file = file;
    }

    public synchronized String getContent() throws IOException {
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            input.lines().forEach(data -> builder.append(data));
            return builder.toString();
        }
    }

    public synchronized String getContent(Predicate<Character> filter) throws IOException {
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            String output = "";
            int data;
            while ((data = input.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
            return output;
        } 
    }
}
