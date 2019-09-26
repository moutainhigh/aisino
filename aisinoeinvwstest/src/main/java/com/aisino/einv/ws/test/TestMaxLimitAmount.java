package com.aisino.einv.ws.test;

public class TestMaxLimitAmount {
    public static void main(String[] args) {
        String maxLimitAmount = "15000001001026801180,915001020003092355";
        String maxLimish = "99,100" ;
        String[] nsrsbhList = maxLimitAmount.split(",");
        String[] maxLimitList = maxLimish.split(",");
        for (int i = 0; i < nsrsbhList.length; i++) {
            System.out.println(nsrsbhList.length);
            System.out.println(nsrsbhList[i]);
            System.out.println(maxLimitList[i]);
        }
    }
}
