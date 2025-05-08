package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");

        try (var input = new URL(this.url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[1024];
            int bytesRead;
            long timeSpent;
            int downloadSpeed;
            int delay;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                timeSpent = System.nanoTime() - downloadAt;
                System.out.println("Read 1024 bytes : " + timeSpent + " nano.");
                downloadSpeed = (int) ((float) 1024 / timeSpent * 1000000);
                if (this.speed < downloadSpeed) {
                    delay = downloadSpeed / speed;
                    Thread.sleep(delay);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Two arguments must be passed");
        }

        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
