package com.aisino.web.util;

import com.aisino.domain.sys.model.Route;
import com.aisino.domain.sys.model.User;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * <p>[描述信息：公共工具类]</p>
 *
 * @author bruce.yang
 * @version 1.0 Created on 2013-9-4 上午9:46:02
 */
public class CommonsUtil {
	/**
	 * <p>功能实现描述:根据所选税务机关取得URL</p>
	 *
	 * @param user
	 * @param swjg_dm
	 * @return String
	 * @author: bruce.yang
	 * @date: Created on 2013-9-4 上午9:46:08
	 */
	public static Route getRouteBySwjg(User user, String swjg_dm) {
		List<Route> routesList = user.getRoutes();
		if (routesList != null) {
			for (int i = 0; i < routesList.size(); i++) {
				Route route = routesList.get(i);
				if (swjg_dm.equals(route.getSwjgDm())) {
					return route;
				}
			}
		}
		return null;
	}

	/**
	 * 获得号称全球唯一的序号 这里将"-"号去掉
	 * 序号实例：dd00f7ac8d184081a7c7ba6e98e1bcb7
	 *
	 * @return
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		//去掉“-”符号
		return StringUtils.replace(s, "-", "");
	}
//    public static void main(String[] args) {
//        System.out.println(getUUID());
//    }
}
