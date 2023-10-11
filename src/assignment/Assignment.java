
package assignment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class Assignment {
    private static final int NUM_CUSTOMERS = 20;
    private static final int NUM_HAIRDRESSERS = 3;
    //private static final int QUEUE_SIZE = 23;
    public static boolean MAX_CUSTOMER_ARRIVED = false;
    
    public static GoldCombs c1 = new GoldCombs(1);
    public static GoldCombs c2 = new GoldCombs(2);
    public static GoldScissors s1 = new GoldScissors(1);
    public static GoldScissors s2 = new GoldScissors(2);
    public static Queue<GoldCombs> goldcombsBox = new LinkedList<>();
    public static Queue<GoldScissors> goldscissorsBox = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Customer> customerQueue = new LinkedBlockingQueue<>();
        goldcombsBox.add(c1);
        goldcombsBox.add(c2);
        goldscissorsBox.add(s1);
        goldscissorsBox.add(s2);
        Customer exit = new Customer(21, customerQueue);

        // Create and start hairdresser threads
        for (int i = 1; i < NUM_HAIRDRESSERS +1; i++) {
            Thread hairdresserThread = new Thread(new Hairdresser(i,customerQueue,goldcombsBox,goldscissorsBox));
            hairdresserThread.start();
        }

        // Enqueue customers
        for (int i = 1; i <= NUM_CUSTOMERS; i++) {
            Customer customer = new Customer(i,customerQueue);
            try {
                TimeUnit.SECONDS.sleep(Time.randomCustomerGetinTime());
                Thread c = new Thread(customer);
                c.start();
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        for (int i = 0; i < NUM_HAIRDRESSERS; i++) {
            customerQueue.put(exit);
        }
        
//        TimeUnit.SECONDS.sleep(3);
//        Customer c = new Customer(22,customerQueue);
//        for (int i = 0; i < NUM_HAIRDRESSERS; i++) {
//            customerQueue.put(c);
//        }

        // Enqueue termination signals for hairdressers
        
    }
}
