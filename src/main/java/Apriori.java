

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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
    static final int minSup=3;
    static final int minC=50;
    static final String path="/home/daovanthien/Desktop/data.xls";
    public static void main(String[] args) {
        Map<String,Integer> mapItemOutputAll=new HashMap<String, Integer>();
        Map<String,Integer> mapItem=new HashMap<String, Integer>();
        countFrequencyFirst(mapItem);
        removeItemNoOk(mapItem);
        System.out.println(mapItem);
        mapItemOutputAll=mapItem;
        while (!mapItem.isEmpty()) {
            mapItem = countFrequencyNext(liNew(convertMapToList(mapItem)));
            removeItemNoOk(mapItem);
            for (String key:mapItem.keySet()) {
                mapItemOutputAll.put(key,mapItem.get(key));
            }
            System.out.println(mapItem);
        }
        System.out.println(mapItemOutputAll);
        tinhDoTin(mapItemOutputAll);

    }
    
    public static List<String> liItemInput(){
        List<String>liItem=new ArrayList<String>();
        liItem.add("ACD");
        liItem.add("BCE");
        liItem.add("ABCE");
        liItem.add("BE");
        return liItem;
    }
    private static List<String> docFile() {
        List<String> a=new ArrayList<String>();
        try {
            Workbook workbook =WorkbookFactory.create(new File(path));
            HSSFSheet sheet  = (HSSFSheet) workbook.getSheetAt(0);
            Row row;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()){
                row = rowIterator.next();
                if(row.getRowNum()<=0)
                    continue;
                String s1=row.getCell(1)+"";
                String s2="";
                for (String s3 :s1.trim().split(","))
                    s2+=s3;
                a.add(s2);
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return a;
    }
    public static void countFrequencyFirst(Map<String,Integer> mapItem){
        for (String string : docFile()) {
            for (int i = 0; i < string.length(); i++) {
                if (mapItem.containsKey(string.charAt(i)+"")) {
                    mapItem.put(string.charAt(i)+"", mapItem.get(string.charAt(i)+"")+1);
                }else
                    mapItem.put(string.charAt(i)+"", 1); 
            }
        }
      

    }

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
            for (String string1 : docFile()) {
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


    public static void tinhDoTin(Map<String,Integer> map){
        for (String key:map.keySet())
            for (String key2:map.keySet())
            {
                if (!key.equals(key2)&&key.length()>1&&key2.length()<key.length())
                    if (checkExists(key,key2)) {
                        double c = 100*(map.get(key) * 1.0) / map.get(key2);
                        if (c>minC){
                            StringBuffer s=new StringBuffer();
                            s.append(key);
                            for (int i=0;i<key2.length();i++){
                                int x=s.indexOf(key2.charAt(i)+"");
                                s.deleteCharAt(x);
                            }

                            System.out.println(key2+"->"+s+" : C="+c );
                        }

                    }

            }
    }
}
