package ru.job4j.buffer;

import ru.job4j.concurrent.SimpleBlockingQueue;

import javax.swing.plaf.nimbus.State;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);

        final Thread consumer = new Thread(
            () -> {
                while (true) {
                    System.out.println(queue.poll());
                }
            }
        );

        Thread producer = new Thread(
            () -> {
                for (int index = 0; index != 3; index++) {
                    queue.offer(index);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        );

        consumer.start();
        producer.start();

        Thread listener = new Thread(
            () -> {
                while (true) {
                    if (consumer.getState() == Thread.State.WAITING && producer.getState() == Thread.State.TERMINATED) {
                        consumer.interrupt();
                        break;
                    }
                }
            }
        );
        listener.start();
    }
}