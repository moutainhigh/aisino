package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;

/**
 * 
 * <p>[描述信息：取得发票上传结果的请求协议bean]</p>
 *
 * @author scott.li
 * @version 1.0 Created on Nov 11, 2013 9:25:33 AM
 */
public class REQUEST_FPKJJG  implements com.aisino.protocol.bean.REQUEST_BEAN {
    
    private String DSPTBM;
    private String FPSCLSH;
    
    public String getDSPTBM() {
        return DSPTBM;
    }
    public void setDSPTBM(String dsptbm) {
        DSPTBM = dsptbm;
    }
    public String getFPSCLSH() {
        return FPSCLSH;
    }
    public void setFPSCLSH(String fpsclsh) {
        FPSCLSH = fpsclsh;
    }
    
}
