package com.aisino.test;

public class TestStringAppend {
	public static void main(String[] args) {
		String a = "博世（BOSCH） BCD-321W(KGN33A2LEC) 321升 风冷无霜 双门冰箱 金属酒架 LCD液晶屏（不锈钢色）";
		String b="博世（BOSCH） BCD-321W(KGN33A2";
		
		String c ="	三星（SAMSUNG）RS542NCAEWW/SC 545升 智能变频 风冷大容量对开门冰箱（雪白色） ";
		String d ="	三星（SAMSUNG）RS542NCAEWW/SC ";
		
//		String e ="三星（SAMSUNG）RS55KBHI0WW/SC 565升 高效节能系列 风冷无霜 双循环制冷 智能变频 双开门冰箱 雪白色 ";
		String e ="三星（SAMSUNG）RS55KBHI0WW/SC-565升 高效节能系列 风冷无霜 双循环制冷 智能变频 双开门冰箱 雪白色";
		String f ="三星（SAM SU N G ）RS55KBH I0W W /SC";
		String replace = f.replace("（", "(");
		String replace2 = replace.replace("）", ")");
		System.out.println("repaldc:::::"+replace2);
		int length = a.length();
		//TODO 
		String prefix = e.substring(0, 25);
		System.out.println("前缀:"+prefix);
		String substring = e.substring(25);//后面
		System.out.println(substring);
		
		StringBuffer sb = new StringBuffer();
		StringBuffer append = sb.append(prefix).append("\r").append(substring);
		String string = append.toString();
		System.out.println("+++++++："+string);
		
		
		
		
//		System.out.println("原长度："+length);//63
//		System.out.println("截取长度："+b.length());//26
//		System.out.println("原长度："+c.length());//53
//		System.out.println("截取长度："+d.length());//27
		System.out.println("原长度："+e.length());//64
		System.out.println("f截取长度："+f.length());//26
		
	}
}
