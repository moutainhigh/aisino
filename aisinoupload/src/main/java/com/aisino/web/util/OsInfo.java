package com.aisino.web.util;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;


public class OsInfo {

	public String getTotalMemory() {
		DecimalFormat dcmFmt = new DecimalFormat("0.0");
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
		totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
		float totals = (float) totalvirtualMemory / (1024 * 1024);
		return dcmFmt.format(totals);
	}

	public String getFreeMemory() {
		DecimalFormat dcmFmt = new DecimalFormat("0.0");
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
		float frees = (float) freePhysicalMemorySize / (1024 * 1024);
		return dcmFmt.format(frees);
	}

	public String getUsedMemory() {
		DecimalFormat dcmFmt = new DecimalFormat("0.0");
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
		totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
		float totals = (float) totalvirtualMemory / (1024 * 1024);
		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
		float frees = (float) freePhysicalMemorySize / (1024 * 1024);
		float compare = (float) (totals - frees);
		return dcmFmt.format(compare);
	}

}
