package com.aisino.itext.util;

import com.aisino.itext.pojo.PdfOut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfOutFileUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfOutFileUtil.class);

	private static PdfOutFileUtil pdfOutFileUtil = null;

	public static PdfOutFileUtil getPdfOutFileUtil() {
		if (pdfOutFileUtil == null) {
			pdfOutFileUtil = new PdfOutFileUtil();
		}
		return pdfOutFileUtil;
	}

	public String outFileToLocal(PdfOut pdfOut) throws IOException {
		String fileUrl = null;
		String swjg_dm = pdfOut.getSwjg_dm();
		LOGGER.info("税务机构代码为>>>>>>>>>>>>"+swjg_dm);
		//修改String串为空的判断
		if( StringUtils.isEmpty(swjg_dm)){
			swjg_dm = "12100000000";
		}
		LOGGER.info("最新税务机构代码为>>>>>>>>>>>>"+swjg_dm);
		StringBuffer root = new StringBuffer(50);
		root.append(SystemConfig.OUTPDFDIR);
		root.append(swjg_dm);
		root.append("/");
		String[] leafs = pdfOut.getKprq().split("-");
		root.append(leafs[0]);
		root.append("/");
		root.append(leafs[1]);
		root.append("/");
		root.append(leafs[2]);
		root.append("/");
		root.append(pdfOut.getFphm().substring(pdfOut.getFphm().length() - 3,
				pdfOut.getFphm().length()));
		root.append("/");
		root.append(pdfOut.getFileName());
		if (makeDir(root)) {
			fileUrl = root.toString();
			FileOutputStream out = new FileOutputStream(new File(fileUrl));
			out.write(pdfOut.getPdfFileData());
			out.flush();
			out.close();
		}
		LOGGER.info("存储文件路径为>>>>>>>>>>"+fileUrl);
		return fileUrl;
	}

	private boolean makeDir(StringBuffer url) {
		LOGGER.info("临时文件路径为>>>>>>>>>>>>"+url.toString());
		File file = new File(url.toString());
		boolean res = file.getParentFile().exists();
		if (!res) {
			res = file.getParentFile().mkdirs();
			LOGGER.info("创建目录标志>>>>>>"+res);
		}
		return res;
	}
}
