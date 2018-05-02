

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc0203
 */
public class Apriori {
    static int minSup=2;
    public static void main(String[] args) {
        Map<String,Integer> mapItem=new HashMap<String, Integer>();
        
        countFrequencyFirst(mapItem);
        removeItemNoOk(mapItem);
        System.out.println(mapItem);
        while (!mapItem.isEmpty()) {
            mapItem = countFrequencyNext(liNew(convertMapToList(mapItem)));
            removeItemNoOk(mapItem);
            System.out.println(mapItem);
        }



    }
    
    public static List<String> liItemInput(){
        List<String>liItem=new ArrayList<String>();
        liItem.add("ACD");
        liItem.add("BCE");
        liItem.add("ABCE");
        liItem.add("BE");
        return liItem;
    }
    
    public static void countFrequencyFirst(Map<String,Integer> mapItem){
        
        // ??m s? l?n xu?t hi?n v� add v�o mapItem
        for (String string : liItemInput()) {
            for (int i = 0; i < string.length(); i++) {
                if (mapItem.containsKey(string.charAt(i)+"")) {
                    mapItem.put(string.charAt(i)+"", mapItem.get(string.charAt(i)+"")+1);
                }else
                    mapItem.put(string.charAt(i)+"", 1); 
            }
        }
      

    }
     //Lo?i b? c�c Item <minSup
    public static void removeItemNoOk(Map<String,Integer> mapItem){
    List<String> temp=new ArrayList<String>();
        for (String key : mapItem.keySet()) {
            if (mapItem.get(key)<minSup) {
                temp.add(key);
            }
        }
        for (String string : temp) {
            mapItem.remove(string);
            
        }
    }
    
    public static List<String> convertMapToList(Map<String,Integer> mapItem){
         List<String>li= new ArrayList<String>();
        for (String key : mapItem.keySet()) {
            li.add(key);
        }
        return li;
    }
    
    public static List<String> liNew(List<String> li){
        List<String>liNew=new ArrayList<String>();
        for (int i = 0; i < li.size(); i++) {
            for (int j = 0; j < li.size(); j++) {
                String s=li.get(j);
                for (int k = 0; k < s.length(); k++) {
                    if (!li.get(i).contains(s.charAt(k)+"")) {
                        String s1=li.get(i)+s.charAt(k);
                        System.out.println(s1);
                        liNew.add(s1);
                    }
                }
            }
        }
        for (int i = 0; i < liNew.size()-1; i++) {
            for (int j = i+1; j < liNew.size(); j++) {
                if (checkExists(liNew.get(i), liNew.get(j))) {
                    liNew.remove(j);
                    j--;
                }
            }
        }
        System.out.println("------------------");
        for (String string : liNew) {
            System.out.println(string);
        }
        return liNew;
    }
    
       public static boolean  checkExists(String s1,String s2){
        for (int i = 0; i < s2.length(); i++) {
            if (!s1.contains(s2.charAt(i)+"")) {
                return false;
            }
        }
        return true;
    }
       
       public static Map<String,Integer> countFrequencyNext(List<String> linew){
        Map<String,Integer> mapTemp=new HashMap<String, Integer>();
        for (String string : linew) {
            for (String string1 : liItemInput()) {
                if (checkExists(string1, string)) {
                     if (mapTemp.containsKey(string)) {
                      mapTemp.put(string, mapTemp.get(string)+1);
                }else
                    mapTemp.put(string,1);
              
            }
            }
        }

           return mapTemp;
       }
}
