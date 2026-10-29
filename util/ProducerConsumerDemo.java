
package util;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerDemo {

    private static final Queue<Integer> queue =
            new LinkedList<>();

    private static final int CAPACITY = 3;

    static class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                synchronized (queue) {
                    try {
                        while (queue.size() == CAPACITY) {
                            queue.wait();
                        }

                        queue.add(i);
                        System.out.println("Produced: " + i);

                        queue.notifyAll();

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
    }

    static class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                synchronized (queue) {
                    try {
                        while (queue.isEmpty()) {
                            queue.wait();
                        }

                        int item = queue.remove();
                        System.out.println("Consumed: " + item);

                        queue.notifyAll();

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Producer-consumer completed.");
    }
}
