/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

import Clueless.Character;
import java.util.Set;

/**
 *
 * @author davis_gigogne
 */
public class GetAvailableCharacters extends Command {
   public Long gameId;
   public Set<Character> characters;
   public GetAvailableCharacters(){
      super(GetAvailableCharacters.class.getSimpleName());
   }
}
