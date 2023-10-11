
package assignment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class Customer implements Runnable {
    private final int customerId;
    private static BlockingQueue<Customer> customerQueue;
    private static final LinkedList<Customer> standingQueue = new LinkedList<>();
    
    public Customer(int customerId, BlockingQueue<Customer> customerQueue) {
        this.customerId = customerId;
        Customer.customerQueue = customerQueue;
    }

    public int getCustomerId() {
        return customerId;
    }
    
    public static void standingCustomerLeave(){
        if (standingQueue.size() <= 1){
            return;
        }
        
        Random random = new Random();
        double randomValue = random.nextDouble();

        boolean leave = randomValue < 0.5;
        System.out.println("snenfjenfenfo");
        if (leave){
            Customer leaving;
            int size = standingQueue.size() -1;
            int indexLeave = random.nextInt(size);
            leaving = standingQueue.get(indexLeave);
            standingQueue.remove(indexLeave);
            System.out.println("Customer " + leaving.getCustomerId() + " is leaving becuz HE CANT TAHAN STANDING");
        } 
    }
    
    public static void customerTaken(){
        if (!standingQueue.isEmpty()){
            Customer customer = standingQueue.poll();
            if (customer != null){
                System.out.println("Customer :" + customer.customerId + " has MOVED FROM STANDING position to SITTING chair.");
                customerQueue.add(customer);
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Customer : " + customerId + " arrived  at time " + Time.currentTime());
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (standingQueue.size() == 5){
                System.out.println("Customer : " + customerId + " LEFT THE SALON as here is too crowded");
            }
            else if (customerQueue.size() == 5){
                System.out.println("Standing Size: "+standingQueue.size());
                System.out.println("Customer " + customerId + " is STANDING...");
                standingQueue.add(this);
            } else {
                System.out.println("Customer " + customerId + " : is SITTING on the waiting chair.");
                customerQueue.put(this);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
