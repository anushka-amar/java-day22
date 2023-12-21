import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

     static final int BUFFER_SIZE = 5;
     static final Queue < Integer > buffer= new LinkedList<>();

    public static void main(String[] args) {
        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        producerThread.start();
        consumerThread.start();
    }
}

/* producer class, produces a value if buffer
is not full */
class Producer implements Runnable {
    public void run() {
        int value = 0;
        while (true) {
            synchronized(ProducerConsumer.buffer) {
                // if buffer is full then waits
                while (ProducerConsumer.buffer.size() == ProducerConsumer.BUFFER_SIZE) {
                    try {
                        ProducerConsumer.buffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Producer produced: " + value);
                ProducerConsumer.buffer.add(value++); //increments the value if new value is added to buffer

                // Notify the consumer that an item is produced
                ProducerConsumer.buffer.notify();
            }
        }
    }
}

/* */
class Consumer implements Runnable {
    public void run() {
        while (true) {
            synchronized( ProducerConsumer.buffer) {
                // if the buffer is full, the thread waits
                while ( ProducerConsumer.buffer.isEmpty()) {
                    try {
                        ProducerConsumer.buffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int value =  ProducerConsumer.buffer.poll(); //removes the top element of the buffer
                System.out.println("Consumer consumed: " + value);

                // Notify the producer that an item is consumed
                ProducerConsumer.buffer.notify();

            }
        }
    }
}



