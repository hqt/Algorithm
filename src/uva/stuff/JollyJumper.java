package uva.stuff;

import java.util.Scanner;
 
public class JollyJumper {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N,dif,prev,cur = 0;
 
        while(in.hasNext()){
            N =in.nextInt();
            cur = in.nextInt();
            boolean[] vis = new boolean[N--];
            boolean good = true;
 
            while(N--!=0){
                prev = cur;
                cur = in.nextInt();
                dif =Math.abs(cur-prev);
                if(dif==0 || dif >=vis.length || vis[dif])
                    good = false;
                else
                vis[dif]=true;
            }
                System.out.println(good ? "Jolly" : "Not jolly");
 
        }
 
    }
}