/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davis_gigogne
 */

public class NotificationManager {
   
   private static NotificationManager _me;
   private Map<String, Map<NotificationEnum, String>> _notifications;
   private Map<String, List<String>> _clientStrings;
   
   private NotificationManager(){
      _notifications = new HashMap<String, Map<NotificationEnum, String>>();
      _clientStrings = new HashMap<String, List<String>>();
      readNotificationFiles("../CluelessNotifications");
      readClientStringFiles("../CluelessClientStrings");
   }
   
   private void readNotificationFiles(String directory){
      //Get all the files in the directory
      File dir = new File(directory);
      File[] lFiles = dir.listFiles();
      
      String[] ll;
      BufferedReader lin;
      EnumMap<NotificationEnum, String> lNotifications;
      
      for(File lFile : lFiles){
         //Prepare an EnumMap for this language's notifications
         lNotifications = new EnumMap<NotificationEnum, String>(NotificationEnum.class);
         
         try {
            //Open a reader to the file
            lin = new BufferedReader(new FileReader(lFile));
            
            while(lin.ready()){
               //'|' will separate the NotificationEnum from the pattern string
               ll = lin.readLine().split("|");
               
               //For robustness's sake, make sure there's only one '|' per line
               if(ll.length != 2){
                  String line = "";
                  for(String s : ll) line += s;
                  throw new Exception("Invalid notification definition: " + line);
               }
               
               //Remove the "" and possible ending , from the pattern string
               ll[1] = ll[1].trim().replaceAll("[\",]", "");
               
               //Add this notification to the map for this language
               lNotifications.put(NotificationEnum.valueOf(ll[0].trim()), ll[1]);
            }
            
            //Done reading the notifications from the file. Add this language's
            //map to the map for all languages
            _notifications.put(lFile.getName().split("\\.")[0], lNotifications);
            
         } catch (Exception ex) {
            Logger.getLogger(NotificationManager.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }
   
   private void readClientStringFiles(String directory){
      System.out.println("readClientStringFiles would be reading from " + directory + " if implemented!");
   }
   
   public static synchronized NotificationManager getInstance(){
      if(_me == null) _me = new NotificationManager();
      return _me;
   }
   
   //The supported languages for notifications and client strings should be
   //the same, but in case they aren't, we'll only return the languages
   //supported by both
   public Set<String> getSupportedLanguages(){
      Set<String> fullSupport = new HashSet<String>(_notifications.keySet());
      fullSupport.retainAll(_clientStrings.keySet());
      return fullSupport;
   }
   
   public String getRawNotification(String lang, NotificationEnum notice){
      return _notifications.get(lang).get(notice);
   }
   
   public List<String> getClientStrings(String lang){
      return _clientStrings.get(lang);
   }
}