/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author davis_gigogne
 */
public abstract class Location extends Card {
   
   protected Position position;
   protected Set<Location> neighbors;
   
   public Location(String name, Position pos){
      super(name);
      position = pos;
      neighbors = new HashSet<Location>();
   }
   
   public Set<Location> getNeighbors(){
      return neighbors;
   }
   
   public void setNeighbors(Set<Location> neighbors){
      this.neighbors = neighbors;
   }
   
   public Position getCoordinate(){
      return position;
   }
   
   public boolean isValidNeighbor(Location loc){
      return neighbors.contains(loc);
   }
   
   @Override
   public boolean equals(Object o){
      boolean retVal = false;
      if(o instanceof Location)
         retVal = super.equals(o) && position.equals(((Location)o).getCoordinate());
      return retVal;
   }
   
   @Override
   public int hashCode(){
      return super.hashCode();
   }
}
