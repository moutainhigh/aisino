package com.aisino.einv.ws.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.RepaintManager;

public class TestRemove {
    public static void main(String[] args) {
        List tempList = new ArrayList<String>();
        tempList.add("1");
        tempList.add("2");
        tempList.add("3");
        tempList.add("4");
        tempList.add("5");
        
        for (int i = 0; i < tempList.size(); i++) {
            if ( 1 == i) {
                tempList.remove(i);
                tempList.remove(i-1);
            }
            
        }
        System.out.println(tempList.toString());
    }
}
