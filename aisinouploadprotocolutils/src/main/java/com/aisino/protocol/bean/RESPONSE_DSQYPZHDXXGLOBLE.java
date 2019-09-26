package com.aisino.protocol.bean;
/**
 * 如果有多条，该类为总信息
 * @author Administrator
 * @date： Sep 23, 2013 6:55:04 PM
 */
public class RESPONSE_DSQYPZHDXXGLOBLE extends RESPONSE_BEAN{
    private RESPONSE_DSQYPZHDXX[] RESPONSE_DSQYPZHDXXS;
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
    public RESPONSE_DSQYPZHDXX[] getRESPONSE_DSQYPZHDXXS() {
        return RESPONSE_DSQYPZHDXXS;
    }

    public void setRESPONSE_DSQYPZHDXXS(RESPONSE_DSQYPZHDXX[] response_dsqypzhdxxs) {
        RESPONSE_DSQYPZHDXXS = response_dsqypzhdxxs;
    }

}
