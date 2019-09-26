package com.aisino.hedwig;

import com.aisino.trans.util.DzfpInterfaceBean;


/**
 * <p>一号店对接hedwig接口</p>
 *
 * @author jerome.wang
 * @version 1.0 Created on 2015-7-13 下午05:19:16
 */
public interface IAisinoDzfpYHDInterface {
	/**
	 * <p>一号店对接接口方法</p>
	 * 
	 * @param dzfpInterfaceBean
	 * @return DzfpInterfaceBean
	 * @author: jerome.wang
	 * @date: Created on 2015-7-13 下午05:19:41
	 */
	DzfpInterfaceBean eiInterface(DzfpInterfaceBean dzfpInterfaceBean);
}
