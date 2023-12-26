import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedResources {

    /* two threads are created via Runnable interface
       and two different objects are linked to it.
       each thread performs its work and then joind main thread */
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        SharedResource sharedResource = new SharedResource();

        Thread[] threads = new Thread[2];
        for (int i = 0; i < 2; i++) {
            threads[i] = new Thread(new Worker(lock, sharedResource));
            threads[i].start();
        }

        try {
            for (Thread thread: threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* locks the resource is a thread is accessing
    * once the work is done, releases the resource
    * so the other thread can access*/
    static class Worker implements Runnable {
        private Lock lock;
        private SharedResource sharedResource;

        public Worker(Lock lock, SharedResource sharedResource) {
            this.lock = lock;
            this.sharedResource = sharedResource;
        }

        public void run() {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    sharedResource.doWork();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /* executes the doWork function, while the shared-resource is locked
    * the work is executed */
    static class SharedResource {
        public void doWork() {
            String threadName = Thread.currentThread().getName();
            System.out.println("Thread-> " + threadName + " is performing work.");
        }
    }
}