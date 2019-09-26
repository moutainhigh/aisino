package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.RESPONSE_PYXZ;

/**
 * 如果有多条，该类为总信息
 * @author Administrator
 * @date： Sep 23, 2013 6:55:04 PM
 */
public class RESPONSE_DSQYPYXXGLOBLE extends com.aisino.protocol.bean.RESPONSE_BEAN {
    private com.aisino.protocol.bean.RESPONSE_PYXZ[] RESPONSE_PYXZS;
    private String RETURNCODE;
    private String RETURNMESSAGE;
    public String getRETURNCODE() {
        return RETURNCODE;
    }
    public void setRETURNCODE(String returncode) {
        RETURNCODE = returncode;
    }
    public String getRETURNMESSAGE() {
        return RETURNMESSAGE;
    }
    public void setRETURNMESSAGE(String returnmessage) {
        RETURNMESSAGE = returnmessage;
    }

    public com.aisino.protocol.bean.RESPONSE_PYXZ[] getRESPONSE_PYXZS() {
        return RESPONSE_PYXZS;
    }

    public void setRESPONSE_PYXZS(RESPONSE_PYXZ[] response_pyxzs) {
        RESPONSE_PYXZS = response_pyxzs;
    }


}
