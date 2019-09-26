package com.aisino.einv.ws.test;

import java.util.Random;

public class RandomString {
    public static String  ramdomString(int i) {
        
        Random random  = new Random(i); 
        StringBuilder sb = new StringBuilder();
        while (true) {
            int k = random.nextInt(27);
            
            if (k ==0 ) {
                break ;
            }
            sb.append((char)('`'+k));
            
        }
        return sb.toString();
        
    }
    
    public static void main(String[] args) {
        System.out.println(ramdomString(-229985452));
        System.out.println(ramdomString(-147909649));
        
    }
}
