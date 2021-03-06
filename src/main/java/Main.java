
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
  
        Map<String,Integer> mapOutPut=new HashMap<String, Integer>();

        int minSUP=2;
        List<String> liInput=new ArrayList<String>();
        liInput.add("ACD");
        liInput.add("BCE");
        liInput.add("ABCE");
        liInput.add("BE");
        
        Map<String,Integer> mapItem=new HashMap<String, Integer>();
        // ??m s? l?n xu?t hi?n v� add v�o mapItem
        for (String string : liInput) {
            for (int i = 0; i < string.length(); i++) {
                if (mapItem.containsKey(string.charAt(i)+"")) {
                    mapItem.put(string.charAt(i)+"", mapItem.get(string.charAt(i)+"")+1);
                }else
                    mapItem.put(string.charAt(i)+"", 1); 
            }
        }
        System.out.println(mapItem);
        
        //Lo?i b? c�c Item <minSup
        List<String> temp=new ArrayList<String>();
        for (String key : mapItem.keySet()) {
            if (mapItem.get(key)<minSUP) {
                temp.add(key);
            }
        }
        for (String string : temp) {
            mapItem.remove(string);
            
        }
        System.out.println(mapItem);
        
        //convert mapItem -> List
        List<String>li= new ArrayList<String>();
        for (String key : mapItem.keySet()) {
            li.add(key);
        }
   

    //Danh s�ch item m?i sau khi gh�p
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
        //
        Map<String,Integer> mapTemp=new HashMap<String, Integer>();
        for (String string : liNew) {
            for (String string1 : liInput) {
                if (checkExists(string1, string)) {
                     if (mapTemp.containsKey(string)) {
                      mapTemp.put(string, mapTemp.get(string)+1);
                }else
                    mapTemp.put(string,1);
              
            }
            }
        }
           System.out.println(mapTemp);

    }
    // check item c� t?n t?i trong list hay ko
    public static boolean  checkExists(String s1,String s2){
        for (int i = 0; i < s2.length(); i++) {
            if (!s1.contains(s2.charAt(i)+"")) {
                return false;
            }
        }
        return true;
    }
}
