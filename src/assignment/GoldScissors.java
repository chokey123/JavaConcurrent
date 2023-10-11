/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author User
 */
public class GoldScissors {
    private int id;
    
    public GoldScissors(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
      
    @Override
    public String toString(){
        return "Gold Scissors " + id;
    }
}
