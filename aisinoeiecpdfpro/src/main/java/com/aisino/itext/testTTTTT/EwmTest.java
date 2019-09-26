package com.aisino.itext.testTTTTT;

import java.io.IOException;

import sun.misc.BASE64Decoder;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

public class EwmTest {
	public static void main(String[] args) {
	  String copy ="Qk1+BgAAAAAAAD4AAAAoAAAAZAAAAGQAAAABAAEAAAAAAEAGAAAAAAAAAAAAAAAAAAACAAAAAAAA///////////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAP////////////////AAAAD////////////////wAAAA////////////////8AAAAAADPAAAzPPzwD////AAAAAAAzwAAMzz88A////wAAAAP/P8P8/DM//A////8AAAAD/z/D/PwzP/wP////AAAAAwM8D/DzPMM/D////wAAAAMDPA/w8zzDPw////8AAAADAzw888P8Dz/P////AAAAAwM8PPPD/A8/z////wAAAAMDM8/PDzAwAw////8AAAADAzPPzw8wMAMP////AAAAA/8/DMAPAwPwD////wAAAAP/PwzADwMD8A////8AAAAAADDzAPPD8zAP////AAAAAAAw8wDzw/MwD////wAAAA//8DDP8APD88////8AAAAP//Awz/ADw/PP////AAAAD8MAD/Aw/zAAD////wAAAA/DAA/wMP8wAA////8AAAAAw8PwAAAP8MwD////AAAAAMPD8AAAD/DMA////wAAAAAAAzPMDMMA/A////8AAAAAAAMzzAzDAPwP////AAAADPPwPz8MM8zDD////wAAAAzz8D8/DDPMww////8AAAAM8AwP8/P/MzwD////AAAADPAMD/Pz/zM8A////wAAAAPz8MPDwM8M/D////8AAAAD8/DDw8DPDPw/////AAAAD/AMMA88D8zPD////wAAAA/wDDAPPA/Mzw////8AAAAPz8PwzPPADMPP////AAAAD8/D8MzzwAzDz////wAAAA//M8PADM8zPDP///8AAAAP/zPDwAzPMzwz////AAAADPDMMDw8MwzMD////wAAAAzwzDA8PDMMzA////8AAAAPDA/88PM/wMwP////AAAADwwP/PDzP8DMD////wAAAA8w8wwzw/zMw8////8AAAAPMPMMM8P8zMPP////AAAADPMM8P8/MzM8A////wAAAAzzDPD/PzMzPAP///8AAAAD888PwD8P/PwD////AAAAA/PPD8A/D/z8A////wAAAAwwMADM/88PwA////8AAAAMMDAAzP/PD8AP////AAAAAz/Azw/wAMzAD////wAAAAM/wM8P8ADMwA////8AAAAMwA8DPDD/Mzwz////AAAADMAPAzww/zM8M////wAAAAwD8PDAwA8z8D////8AAAAMA/DwwMAPM/A/////AAAADA8Dz8zMAwD/D////wAAAAwPA8/MzAMA/w////8AAAAPwMPM/w8wzMP/////AAAAD8DDzP8PMMzD/////wAAAAPzAADD8/8wAPP///8AAAAD8wAAw/P/MADz////AAAAD//w8//wz8P//////wAAAA//8PP/8M/D//////8AAAAAADMzMzMzMwAD////AAAAAAAzMzMzMzMAA////wAAAAP/MD88w/A/P/P///8AAAAD/zA/PMPwPz/z////AAAAAwMz//zzAM8wM////wAAAAMDM//88wDPMDP///8AAAADAzAAD/MwzzAz////AAAAAwMwAA/zMM8wM////wAAAAMDPDDA/DM/MDP///8AAAADAzwwwPwzPzAz////AAAAA/888/APwDM/8////wAAAAP/PPPwD8AzP/P///8AAAAAADPDM8P8zwAD////AAAAAAAzwzPD/M8AA////wAAAA";
	  Image ewmImage = null;
        byte[] imageData;
		try {
			imageData = new BASE64Decoder().decodeBuffer(copy);
			if (imageData != null) {
				try {
					ewmImage = Image.getInstance(imageData);
				} catch (BadElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
