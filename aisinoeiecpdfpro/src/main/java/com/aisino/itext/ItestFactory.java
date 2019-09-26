package com.aisino.itext;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

import com.aisino.itext.dao.IItextDao;
import com.aisino.itext.pojo.StencilPlageMx;
import com.aisino.itext.pojo.StencilPlate;
import com.aisino.itext.util.BeanToMapUtil;
import com.aisino.itext.util.SystemConfig;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class ItestFactory {
    private static ItextDocument document = null;
    private static DecimalFormat format= new DecimalFormat("0.00"); 
    private static int xm_len=29;
    public static ItextDocument getDocument() {
        if (document == null) {
            document = new ItestFactory().new ItextDocument();
        }
        return document;
    }

    public class ItextDocument {
		Logger LOGGER = LoggerFactory.getLogger(ItextDocument.class);

		public byte[] producePDF(StencilPlate stencilPlate,IItextDao itextDao,Map fpmbMap) {
			LOGGER.info("\n发票代码为:" + stencilPlate.getFp_dm()+ "发票号码为:" + stencilPlate.getFp_hm() + "的电子发票开始创建..");
            ByteArrayOutputStream baos=null;
            byte[] ret=null;
            try {
            	 //String swjgdm=stencilPlate.getSz_swjg_dm(); (模板需要挂到纳税人下 by bourne.lv)
                 String nsrsbh =stencilPlate.getXhdwnsrsbh().trim();
            	 StencilPlate copy=stencilPlate.copy();
                 copy.setStencilPlageMxs(rebuild(copy.getStencilPlageMxs()));
                 HashMap<String, Object> map = new HashMap<String, Object>();
                 BeanToMapUtil.convertBean(copy, map);
                PdfReader reader=null;
                boolean flag=false;
                int len=copy.getStencilPlageMxs().length;
                Object obj = null;
                if (len <= 8) {
                	//obj = itextDao.getTemplate(swjgdm, "5");
                    obj = itextDao.getTemplate(nsrsbh, "8");
                	if(obj==null){
                		throw new Exception("未找到符合条件的模版");
                	}
                	fpmbMap.putAll((Map)obj);
                    reader = new PdfReader(fpmbMap.get("fpfile").toString());
//                } else if(8<len && len<= 26){
//                	//obj = itextDao.getTemplate(swjgdm, "26");
////                    obj = itextDao.getTemplate(nsrsbh, "26");
//                    final Map<String, String> param = new HashMap();
//                    param.put("fpfile", "d:/1234.pdf");
//                    param.put("twmleft", "3");
//                    param.put("twmright", "4");
//                    param.put("qzleft", "12");
//                    param.put("qztop", "3" );
//                    param.put("qzright", "4");
//                    param.put("qzbottom",  "5");
//                    param.put("qzpageindex", "9");
//                    obj=param;
//                	if(obj==null){
//                		throw new Exception("未找到符合条件的模版");
//                	}
//                	fpmbMap.putAll((Map)obj);
//                    reader = new PdfReader(fpmbMap.get("fpfile").toString());
                }else{
                	//obj = itextDao.getTemplate(swjgdm, "0");
                    obj = itextDao.getTemplate(nsrsbh, "0");
                	if(obj==null){
                		throw new Exception("未找到符合条件的模版");
                	}
                	fpmbMap.putAll((Map)obj);
                    reader = new PdfReader(fpmbMap.get("fpfile").toString());
                }
                if(len>8){
                	flag=true;
                	if("".equals(map.get("kplx"))){
                	    map.put("spmc","(详见销货清单)");
                	}else{
                	    map.put("spmc","详见对应正数发票及清单");
                	}
                	map.put("je",map.get("hjje").toString().substring(1,map.get("hjje").toString().length()));
                	map.put("se",map.get("hjse").toString().substring(1,map.get("hjse").toString().length()));
                	map.put("slv",map.get("slbz").toString());
                }
                
                int pageNo=0;
                baos = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, baos);
                AcroFields form = stamp.getAcroFields();
            
                Image ewmImage = null;
//                TDCodeImageUtil codeImageUtil = TDCodeImageUtil.newInstance();
//                ImageCode imageCode = codeImageUtil.paintImage(copy.getEwm());
//                if (imageCode != null) {
//                    byte[] imageData = imageCode.getImageData();
//                }
                byte[] imageData = new BASE64Decoder().decodeBuffer(copy.getEwm());
//                File f = new File("f:/aabb.bmp");
//                FileInputStream fil = new FileInputStream(f);
//                byte[] buf = new byte[(int) f.length()];
//                fil.read(buf);
//                byte[] imageData = buf;
                if (imageData != null) {
                    ewmImage = Image.getInstance(imageData);
                }
                if(null != ewmImage){
                    ewmImage.scaleAbsolute(SystemConfig._2wm_width,SystemConfig._2wm_heigth);
                    float twmleft = SystemConfig.A5_2wm_left;
                	float twmright = SystemConfig.A5_2wm_right;
                    if (len<= 8) {
                    	//修改String串为空的判断
                    	 
                    	if(fpmbMap.get("twmleft")!=null && StringUtils.isNotEmpty(fpmbMap.get("twmleft").toString())){
                    		twmleft = new Float(fpmbMap.get("twmleft").toString());
                    	}
                    	if(fpmbMap.get("twmright")!=null &&  StringUtils.isNotEmpty(fpmbMap.get("twmright").toString())){
                    		twmright =  new Float(fpmbMap.get("twmright").toString());
                    	}
                        ewmImage.setAbsolutePosition(twmleft,twmright);
                    }else if(len> 8){
                    	twmleft = SystemConfig.A6_2wm_left;
                    	twmright = SystemConfig.A6_2wm_right;
                    	//修改String串为空的判断
                    	if(fpmbMap.get("twmleft")!=null && StringUtils.isNotEmpty(fpmbMap.get("twmleft").toString())){
                    		twmleft = new Float(fpmbMap.get("twmleft").toString());
                    	}
                    	if(fpmbMap.get("twmright")!=null &&  StringUtils.isNotEmpty(fpmbMap.get("twmright").toString())){
                    		twmright =  new Float(fpmbMap.get("twmright").toString());
                    	}
                        ewmImage.setAbsolutePosition(twmleft,twmright);
                    }else{
                    	twmleft = SystemConfig.A4_2wm_left;
                    	twmright = SystemConfig.A4_2wm_right;
                    	//修改String串为空的判断
                    	if(fpmbMap.get("twmleft")!=null && StringUtils.isNotEmpty(fpmbMap.get("twmleft").toString())){
                    		twmleft = new Float(fpmbMap.get("twmleft").toString());
                    	}
                    	if(fpmbMap.get("twmright")!=null &&  StringUtils.isNotEmpty(fpmbMap.get("twmright").toString())){
                    		twmright =  new Float(fpmbMap.get("twmright").toString());
                    	}
                        ewmImage.setAbsolutePosition(twmleft,twmright);
                    }
                    ewmImage.setAlignment(Image.ALIGN_RIGHT);
                    PdfContentByte over;
                    over = stamp.getOverContent(1);
                    over.addImage(ewmImage);
                    setField(form, map,pageNo++,flag);
                    stamp.setFormFlattening(true);
                    stamp.close();
					LOGGER.info("发票代码为:" + copy.getFp_dm() + "发票号码为:"  + copy.getFp_hm() + "的电子发票生成成功..\n ");
                }
                
                if(flag){
                	List<byte[]> list=new ArrayList<byte[]>();
                	list.add(baos.toByteArray());
                	baos.close();
                	int totalPage=0;
                	if(len%25==0){
                		totalPage=len/25;
                	}else{
                		totalPage=len/25+1;
                	}
 //               	map.put("ym", ""+totalPage);
                	map.put("ys", ""+totalPage);
                	//obj = itextDao.getTemplate(swjgdm, "");
                    obj = itextDao.getTemplate(nsrsbh, "");
                	if(obj==null){
                		throw new Exception("未查询到清单模版");
                	}
                	Map qdMap = (Map)obj;
                	for(int i=0;i<totalPage;i++){
                		baos=new ByteArrayOutputStream();
                		reader = new PdfReader(qdMap.get("fpfile").toString());//reader = new PdfReader(SystemConfig.pyUrl + SystemConfig.LISTPYFILENAME);
                		stamp = new PdfStamper(reader, baos);
                		form = stamp.getAcroFields();
	            		setField(form, map,pageNo++,flag);
	                    stamp.setFormFlattening(true);
	                    stamp.close();
	                    list.add(baos.toByteArray());
	                    baos.close();
                	}
                	baos=new ByteArrayOutputStream();
                	mergePdfFiles(list, baos);
                	ret=baos.toByteArray();
                	baos.close();
                }else{
                	ret=baos.toByteArray();
                	baos.close();
                }
                
            } catch (Exception e) {
            	e.printStackTrace();
				LOGGER.error("发票代码为:" + stencilPlate.getFp_dm() + "发票号码为:" + stencilPlate.getFp_hm() + "的电子发票生成失败.." + e.getMessage() + "\n");
            }
            return ret;
        }

        public void setField(AcroFields form, HashMap<String, Object> fieldMap,int pageNo,boolean flag) {
        	 try {
        	BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(bfChinese);
        	if(pageNo==0){
        		Set<Entry<String, Object>> it = (Set<Entry<String, Object>>)fieldMap.entrySet();
    			Iterator<Entry<String, Object>> itr = it.iterator();
                while (itr.hasNext()) {
                        Entry<String, Object> temp = itr.next();
                        if(temp.getKey().equals("mapMx")&&!flag){
                        	Map map=(Map)temp.getValue();
                    		Set<Entry<String, String>> set = (Set<Entry<String, String>>)map.entrySet();
                    		for (Entry<String, String> en:set) {
                    			String[] args=en.getKey().split("_");
                        		int num=Integer.valueOf(args[1]);
                    			if(num==0){
                    				boolean b = form.setField(args[0], en.getValue().toString());
                    				if(LOGGER.isDebugEnabled())
										LOGGER.debug(temp.getKey() + ">>>>>" + b + "<<<<<<" + en.getValue().toString());
                    			}else{
                    				boolean b = form.setField(args[0]+num, en.getValue().toString());
                    				if(LOGGER.isDebugEnabled())
										LOGGER.debug(temp.getKey() + ">>>>>" + b + "<<<<<<" + en.getValue().toString());
                    			}
							}
                    	}else{
                    	    boolean b = form.setField(temp.getKey(), temp.getValue().toString());
                    		if(!b){
                    		    LOGGER.debug(temp.getKey() + ">>>>>" + b + "<<<<<<" + temp.getValue().toString());
                    		}
        					if(LOGGER.isDebugEnabled())
								LOGGER.debug(temp.getKey() + ">>>>>" + b + "<<<<<<" + temp.getValue().toString());
                    	}
                }
        	}else{
 //       		fieldMap.put("ys", pageNo);
        		fieldMap.put("ym", pageNo);
        		Set<Entry<String, Object>> it = (Set<Entry<String, Object>>)fieldMap.entrySet();
    			Iterator<Entry<String, Object>> itr = it.iterator();
    			double jexj=0d;
    			double sexj=0d;
                while (itr.hasNext()) {
                        Entry<String, Object> temp = itr.next();
                        if(temp.getKey().equals("mapMx")&&flag){
                        	Map map=(Map)temp.getValue();
                        	Set<Entry<String, String>> set = (Set<Entry<String, String>>)map.entrySet();
                    		for (Entry<String, String> en:set) {
                    			String[] args=en.getKey().split("_");
                        		int num=Integer.valueOf(args[1]);
                        		if(num<pageNo*25&&num>=(pageNo-1)*25){
                        				boolean b = form.setField(args[0]+num%25, en.getValue().toString());
                        				if("je".equals(args[0])){
                                			String val=en.getValue();
                                			if(StringUtils.isNotEmpty(val)){
                                				jexj+=Double.valueOf(val);
                                			}
                                		}
                        				if("se".equals(args[0])){
                                            String val=en.getValue();
                                            if(StringUtils.isNotEmpty(val)){
                                                sexj+=Double.valueOf(val);
                                            }
                                        }
                        		}
                        	}
                    	}else{
                    		boolean b = form.setField(temp.getKey(), temp.getValue().toString());
        					if(LOGGER.isDebugEnabled())
								LOGGER.debug(temp.getKey() + ">>>>>" + b + "<<<<<<" + temp.getValue().toString());
                    	}
                }
                String jexjFormat=format.format(jexj);
                String sexjFormat=format.format(sexj);
                if(pageNo==1){
                    fieldMap.put("je_zj", jexjFormat);
                    fieldMap.put("se_zj", sexjFormat);
                }else{
                    fieldMap.put("je_zj", format.format(jexj+Double.valueOf(fieldMap.get("je_zj").toString())));
                    fieldMap.put("se_zj", format.format(sexj+Double.valueOf(fieldMap.get("se_zj").toString())));
                }
                boolean b = form.setField("je_xj", jexjFormat);
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug("jexj>>>>>" + b + "<<<<<<" + jexjFormat);
                b = form.setField("se_xj", sexjFormat);
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug("sexj>>>>>" + b + "<<<<<<" + sexjFormat);
                b = form.setField("je_zj", fieldMap.get("je_zj").toString());
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug("jexj>>>>>" + b + "<<<<<<" + fieldMap.get("je_zj").toString());
                b = form.setField("se_zj", fieldMap.get("se_zj").toString());
//				b = form.setField("zj", (String)fieldMap.get("kphjje"));
				if(LOGGER.isDebugEnabled())
					LOGGER.debug("sezj>>>>>" + b + "<<<<<<" + fieldMap.get("se_zj").toString());
                
        	}
        	 } catch (IOException e) {
				 LOGGER.error("PDF录入值出错" + e.getMessage());
             } catch (DocumentException e) {
				 LOGGER.error("PDF录入值出错" + e.getMessage());
             }
        	
        }
       

        public String getFileName(StencilPlate stencilPlate) {
            return stencilPlate.getFp_dm() + stencilPlate.getFp_hm() + ".PDF";
        }

        public boolean mergePdfFiles(List<byte[]> files, ByteArrayOutputStream output) {
    		try {
    			Document document =new Document() ;
    			PdfCopy copy = new PdfCopy(document, output);
    			document.open();
    			PdfReader reader;
    			for (int i = 0; i < files.size(); i++) {
    				reader = new PdfReader(files.get(i));
    				int n = reader.getNumberOfPages();
    				for (int page = 0; page < n; ) {
    					document.newPage();
    					copy.addPage(copy.getImportedPage(reader, ++page));
    				}
    			}
    			document.close();
    			return true;
    		} catch (IOException e) {
    			e.printStackTrace();
    			return false;
    		} catch (DocumentException e) {
    			e.printStackTrace();
    			return false;
    		}
    	}
    }
    public static StencilPlageMx[] rebuild(StencilPlageMx[] mxs) throws UnsupportedEncodingException{
    	List<StencilPlageMx> mxList=new ArrayList<StencilPlageMx>();
    	for(StencilPlageMx mx:mxs){
    		if(mx!=null){
    			String xm = mx.getSpmc();
    			int len=0;
    			int start=0;
    			for(int i=0;i<xm.length();i++){
				    if(xm.charAt(i)>256){
				       len+=2;
				    }else{
				       len++;
				    }
				    if(len>xm_len){
				    	//TODO 处理商品名称  只处理首行 2017-06-09 FWH
						if (start == 0) {
							String oneLine = xm.substring(start, i + 1);
//							int length = oneLine.length();
//							if (length >= 26) {
								String replace = oneLine.replaceAll("（", "(");
								String replace2 = replace.replaceAll("）", ")");
								mx.setSpmc(replace2);
//							} else {
//								mx.setSpmc(oneLine);
//							}
						} else {
							mx.setSpmc(xm.substring(start, i + 1));
						}
				    	mxList.add(mx);
				    	mx=new StencilPlageMx();
				    	start=i+1;
				    	len=0;
				    }
				    if(i==xm.length()-1&&len>0){
				    	mx.setSpmc(xm.substring(start, i+1));
				    	mxList.add(mx);
				    }
    			}
        	}
    	}
    	StencilPlageMx[] news=new StencilPlageMx[mxList.size()];
    	mxList.toArray(news);
    	return news;
    }
    public static void main(String[] args) throws UnsupportedEncodingException{
    	
    }
}
