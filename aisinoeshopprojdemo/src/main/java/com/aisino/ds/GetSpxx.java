package com.aisino.ds;

public class GetSpxx {
	public static String getJe(Double je) {
		try {
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
			return df.format(je);
		} catch (Exception ce) {
			return "0.00";
		}
	}
	public Wpxx retSpxx(String wpids) {
		int wpid = Integer.parseInt(wpids);
		Wpxx wpxx = new Wpxx();
		if (wpid == 1) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(2399.00));
			wpxx.setWpname("苹果（Apple） iPad mini MD531CH/A 7.9英寸平板电脑  ");
		}
		if (wpid == 2) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(488.00));
			wpxx.setWpname("台电（Teclast） P85双核 8寸 16G 1024*768 纯平超薄 ");
		}
		if (wpid == 3) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(1499.00));
			wpxx.setWpname("华为（HUAWEI）MediaPad 10 Link wifi版/10.1英寸");
		}
		if (wpid == 4) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(1499.00));
			wpxx.setWpname("三星TAB2-P5110 10.1英寸平板电脑");
		}
		if (wpid == 5) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(3528.00));
			wpxx.setWpname("苹果（Apple）第4代 iPad MD513CH/A 9.7英寸平板电脑");
		}
		if (wpid == 6) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(1099.00));
			wpxx.setWpname("台电（Teclast）A11 10.1寸 16G 1280*800 第二代IPS硬屏 ");
		}
		if (wpid == 7) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(269.00));
			wpxx.setWpname("智酷(Witcool) x5 7英寸超薄平板电脑 白色超值平板 ");
		}
		if (wpid == 8) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(299.00));
			wpxx.setWpname("爱国者（aigo）M608 超薄6英寸平板电脑 5点触控电容屏");
		}
		if (wpid == 9) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(799.00));
			wpxx.setWpname("台电（Teclast） P88四核 8寸 八显四核 64位 16G");
		}
		if (wpid == 10) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(2699.00));
			wpxx.setWpname("苹果（Apple）iPad 2 MC979CH/A 9.7英寸平板电脑");
		}
		if (wpid == 11) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(1299.00));
			wpxx.setWpname("三星TAB2-P3110 7英寸平板电脑");
		}
		if (wpid == 12) {
			wpxx.setWpid(wpid);
			wpxx.setWpjg(new Double(1999.00));
			wpxx.setWpname("三星TAB2-P5110 10.1英寸平板电脑");
		}
		wpxx.setType(0);
		return wpxx;
	}
}
