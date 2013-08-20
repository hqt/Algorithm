package uva.datastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class HardwoodSpecies {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cases=Integer.parseInt(br.readLine());
        br.readLine();
        
        for(int i=0;i<cases;i++){
            if(i>0)
                System.out.println();
        
            HashMap<String,Integer> hm=new HashMap<String, Integer>();
            LinkedList<String> list=new LinkedList<String>();
            
            int counter=0;
            String temp;
            
            while((temp=br.readLine())!=null){
                temp=temp.trim();
                if(temp.equals("")){
                    break;
                }else{
                   if(hm.containsKey(temp)){
                       int ind=hm.get(temp);
                       hm.put(temp, ind+1);
                   }else{
                       hm.put(temp, 1);
                       list.add(temp);
                   } 
                   counter++;
                }
            }
            
            Collections.sort(list);
            while(!list.isEmpty()){
                String str=list.remove();
                double val=(double)hm.get(str)*100/counter;
                System.out.printf("%s %.4f\n", str,val);
            }
        }
    }
}