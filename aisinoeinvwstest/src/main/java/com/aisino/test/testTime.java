package com.aisino.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class testTime {
	public static void main(String[] args) {
		final Date time = new Date();
		Calendar d = Calendar.getInstance();
		d.setTime(time);
		d.set(d.HOUR_OF_DAY, 0);
		d.clear(d.MINUTE);
		d.clear(d.SECOND);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curretnTime = sf.format(d.getTime());// 当前整点时间,只传入日期，时间默认为零点
		System.out.println(curretnTime);//2017-10-24 00:00:00  将时间设置为零点
	}
}
