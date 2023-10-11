/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.Random;

public class Time {
    private static final long startTime = System.currentTimeMillis();

    public static int randomHaircutTime() {
        Random random = new Random();
        return 3 + random.nextInt(3); // Generates a random value between 3 and 6
    }
    
    public static int randomCustomerGetinTime() {
        Random random = new Random();
        return random.nextInt(3); // Generates a random value between 0 and 2
    }
     
    public static long currentTime() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - startTime) / 1000;
    }
}
