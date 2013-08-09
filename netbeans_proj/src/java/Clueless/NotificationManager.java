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
      File dir = new File(directory);
      File[] lFiles = dir.listFiles();
      
      String[] ll;
      BufferedReader lin;
      EnumMap<NotificationEnum, String> lNotifications;
      
      for(File lFile : lFiles){
         lNotifications = new EnumMap<NotificationEnum, String>(NotificationEnum.class);
         
         try {
            lin = new BufferedReader(new FileReader(lFile));
            
            while(lin.ready()){
               ll = lin.readLine().split("|");
               
               if(ll.length != 2){
                  String line = "";
                  for(String s : ll) line += s;
                  throw new Exception("Invalid notification definition: " + line);
               }
               
               ll[1] = ll[1].trim().replaceAll("[\",]", "");
               lNotifications.put(NotificationEnum.valueOf(ll[0].trim()), ll[1]);
            }
            
            _notifications.put(lFile.getName().split("\\.")[0], lNotifications);
            
         } catch (Exception ex) {
            Logger.getLogger(NotificationManager.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }
   
   private void readClientStringFiles(String directory){
      System.out.println("readClientStringFiles would be reading from " + directory + " if implemented!");
   }
   
   public static NotificationManager getInstance(){
      if(_me == null) _me = new NotificationManager();
      return _me;
   }
   
   public Set<String> getSupportedLanguages(){
      return _notifications.keySet();
   }
   
   public String getRawNotification(String lang, NotificationEnum notice){
      return _notifications.get(lang).get(notice);
   }
   
   public List<String> getClientStrings(String lang){
      return _clientStrings.get(lang);
   }
}