package com.aisino.test;

public class insertTest {
	public static void main(String[] args) {
		String str ="三星(SAM SU N G )RS542N CAEW W /SC545升智能变频风冷大容量对开门冰箱(雪白色)";
		String a = str.substring(0,35)+"\n"+str.substring(35, str.length());
		System.out.println(a);
	}
}
