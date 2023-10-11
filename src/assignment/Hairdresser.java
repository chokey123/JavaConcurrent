package assignment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class Hairdresser implements Runnable {
    private final BlockingQueue<Customer> customerQueue;
    private int id;
    
    
    Queue<GoldCombs> goldcombsBox = new LinkedList<>();
    Queue<GoldScissors> goldscissorsBox = new LinkedList<>();

    public Hairdresser(int id, BlockingQueue<Customer> customerQueue) {
        this.id = id;
        this.customerQueue = customerQueue;
    }
    
    public Hairdresser(int id, BlockingQueue<Customer> customerQueue, Queue<GoldCombs> goldcombsBox, Queue<GoldScissors> goldscissorsBox) {
        this.id = id;
        this.customerQueue = customerQueue;
        this.goldcombsBox = goldcombsBox;
        this.goldscissorsBox = goldscissorsBox;
    }
  
    @Override
    public void run() {
        while (!Assignment.MAX_CUSTOMER_ARRIVED) {
            try {
                Customer customer = customerQueue.take();
                Customer.customerTaken();
                
                if (customer.getCustomerId() == 20){
                    Assignment.MAX_CUSTOMER_ARRIVED = true;
                }
                
                if (customer.getCustomerId() == 21){
                    
                    if (customerQueue.isEmpty()){
                        return;
                    } else {
                        continue;
                    }
                }
                
                Customer.standingCustomerLeave();
                
                System.out.println("Hairdresser "+ id + " : invite Customer " + customer.getCustomerId() + " to sit at chair "+ id);
                
                GoldCombs comb;
                comb = getCombs();
                
                GoldScissors scis;
                scis = getScissors();
                
                // Process the customer (haircut)
                cutHair(customer.getCustomerId());
                
                Customer.standingCustomerLeave();
                
                releaseTools(comb,scis);
                
                TimeUnit.MILLISECONDS.sleep(500);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (Assignment.MAX_CUSTOMER_ARRIVED){ 
           while (!customerQueue.isEmpty()){
               
               Customer customer;
               try {
                   customer = customerQueue.take();
                   
                    GoldCombs comb;
                    comb = getCombs();
                
                    GoldScissors scis;
                    scis = getScissors();
                   
                   cutHair(customer.getCustomerId());
                   
                   releaseTools(comb,scis);
               } catch (InterruptedException ex) {
                   Logger.getLogger(Hairdresser.class.getName()).log(Level.SEVERE, null, ex);
               }
                       
           }  
           System.out.println("Hairdresser " + id + " Bye bye !!!");
           
             
        }
    }
    
    public synchronized GoldCombs getCombs() throws InterruptedException{
        synchronized (goldcombsBox){
            if (goldcombsBox.isEmpty()){
                System.out.println("Hairdresser " + id + " WAITING for the GOLD COMBS");
                goldcombsBox.wait();
            }
        }
        GoldCombs comb = goldcombsBox.poll();
        System.out.println("Hairdresser "+id+" acquired " + comb.toString());
        return comb;        
                
    }
    
    public synchronized GoldScissors getScissors() throws InterruptedException {
        synchronized (goldscissorsBox){
            if (goldscissorsBox.isEmpty()){
                System.out.println("Hairdresser " + id + " WAITING for the GOLD SCISSORS");
                goldscissorsBox.wait();
            }
        }
        GoldScissors scis = goldscissorsBox.poll();
        System.out.println("Hairdresser "+id+" acquired " + scis.toString());
                
        return scis;        
    }
    
    public synchronized void releaseTools(GoldCombs comb, GoldScissors scis){
         synchronized (goldcombsBox){
                    goldcombsBox.add(comb);
                    System.out.println("Hairdresser " + id +": REUTRN GOLD COMBS "+ comb.toString());
                    goldcombsBox.notify();
                }
         synchronized (goldscissorsBox){
             goldscissorsBox.add(scis);
             System.out.println("Hairdresser " + id +": REUTRN GOLD SCISSORS "+ scis.toString());
             goldscissorsBox.notify();
         }
    }
    
    public void cutHair(int customerId){
        try {
            int haircutTime = Time.randomHaircutTime() * 1000;
            int quaterTime = haircutTime/4;
            System.out.println("Hairdresser "+ id + " : STARTED cutting hair for Customer " + customerId + " 0%");
            TimeUnit.MILLISECONDS.sleep(quaterTime);
            System.out.println("Hairdresser "+ id + " : cutting hair for Customer " + customerId + " 25%");
            TimeUnit.MILLISECONDS.sleep(quaterTime);
            System.out.println("Hairdresser "+ id + " : cutting hair for Customer " + customerId + " 50%");
            TimeUnit.MILLISECONDS.sleep(quaterTime);
            System.out.println("Hairdresser "+ id + " : cutting hair for Customer " + customerId + " 75%");
            TimeUnit.MILLISECONDS.sleep(quaterTime);
            System.out.println("Hairdresser "+ id + " : COMPLETED haircut for Customer " + customerId + " 100% at time " + Time.currentTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(Hairdresser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
