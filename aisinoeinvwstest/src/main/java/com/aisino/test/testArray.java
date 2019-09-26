package com.aisino.test;


public class testArray {
	public static void main(String[] args) {
		
		String nsrsbhCH ="111111,22222";
		String[] split = nsrsbhCH.split(",");
		for (int i = 0; i < split.length; i++) {
			System.out.println(split[i]);
		}
	}
}
