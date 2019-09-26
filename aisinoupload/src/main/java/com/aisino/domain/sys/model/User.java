package com.aisino.domain.sys.model;

import com.aisino.domain.base.model.BaseObject;

import java.util.Date;
import java.util.List;

public class User extends BaseObject {
	private static final long serialVersionUID = 1L;
	private Integer userId;// ID
	private Integer roleid;//角色id
	private String userName;// 名称
	private String userAccount;// 帐号
	private String password;// 密码
	private Date regTime;// 注册时间
	private String isValid;// 有效标志
	private String newpwd;// 新密码
	private Date lastLoginTime;
	/**
	 * 用户类型  0为电商平台用户，1为电商企业用户
	 */
	private String userType;
	/**
	 * 纳税人识别号 当用户类型为电商企业时，该项必录
	 */
	private String nsrsbh;
	private Integer logins;
	private List<Menu> menus;
	/**
	 * 系统路由列表
	 */
	private List<Route> routes;

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public String getPassword() {
		return password;
	}

	public String getValidityDesc() {
		if (isValid != null) {
			return "Y".equals(isValid) ? "有效" : "无效";
		} else {
			return null;
		}
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserAccount() {
		return userAccount;
	}


	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}


	public Date getRegTime() {
		return regTime;
	}


	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}


	public String getIsValid() {
		return isValid;
	}


	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}


	public Date getLastLoginTime() {
		return lastLoginTime;
	}


	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	public Integer getLogins() {
		return logins;
	}


	public void setLogins(Integer logins) {
		this.logins = logins;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	/**
	 * @return 用户类型 0为电商平台用户，1为电商企业用户
	 */
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}
}
