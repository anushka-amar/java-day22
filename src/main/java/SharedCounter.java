/* implements the runnable interface
* that uses the run method to run a thread */
class MyCounter implements Runnable{
    int count;
    
    // increments the counter variable
    public synchronized void run(){
        for(int i=1; i<=100; i++){
            count++;
        }
    }

    public int getCount(){
        return count;
    }
}

public class SharedCounter {
    public static void main(String[] args) throws InterruptedException {
        MyCounter counter = new MyCounter();
        Thread t1 = new Thread(counter); //Thread object takes Runnable target
        Thread t2 = new Thread(counter);

        t1.start(); //checks for run method in the thread and executes it
        t2.start();

        t1.join(); //once t1 thread is completed joins with the main thread
        t2.join();

        System.out.println(counter.getCount());
    }
}
