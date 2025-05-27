package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class SaveFileContent {

    private final File file;

    public SaveFileContent(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        }
    }
}
