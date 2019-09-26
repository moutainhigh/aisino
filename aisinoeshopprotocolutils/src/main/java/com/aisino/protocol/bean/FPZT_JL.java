/**
 * 
 */
package com.aisino.protocol.bean;

import com.aisino.protocol.bean.*;
import com.aisino.protocol.bean.SPH_JL;

/**
 * @author fanlb
 * 
 */
public class FPZT_JL
{
	private com.aisino.protocol.bean.FPT FPT;
	private com.aisino.protocol.bean.SPH_JL[] SPH_JLS;

	public com.aisino.protocol.bean.FPT getFPT()
	{
		return FPT;
	}

	public void setFPT(com.aisino.protocol.bean.FPT fpt)
	{
		FPT = fpt;
	}

	public com.aisino.protocol.bean.SPH_JL[] getSPH_JLS()
	{
		return SPH_JLS;
	}

	public void setSPH_JLS(SPH_JL[] sph_jls)
	{
		SPH_JLS = sph_jls;
	}
}
