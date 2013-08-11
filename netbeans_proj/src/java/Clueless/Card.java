/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

/**
 *
 * @author davis_gigogne
 */
public abstract class Card {
   
   protected String name;
   
   public Card(String name){
      this.name = name;
   }
   
   public String getName(){
      return name;
   }
   
   @Override
   public String toString(){
      return name;
   }
}
