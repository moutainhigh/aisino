package com.aisino.domain.sys.model;

import java.io.Serializable;

import static com.google.common.base.Objects.toStringHelper;

/**
 * 系统路由实体对象
 *
 * @author 李春辉
 *         Aug 27, 2013 3:01:12 PM
 */
public final class Route implements Serializable {

	private static final long serialVersionUID = -2465423449262420333L;

	//主键id
	private String id;

	//请求url
	private String url;

	//税务机关代码
	private String swjgDm;

	//有效标志
	private String yxbz;

	//请求方式
	private String qqfs;

	//请求方法
	private String method;

	//用户名
	private String username;

	//密码
	private String pwd;

	//税务机关名称
	private String swjgMc;

	//电商平台编码
	private String dsptbm;

	public String getDsptbm() {
		return dsptbm;
	}

	public void setDsptbm(String dsptbm) {
		this.dsptbm = dsptbm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSwjgDm() {
		return swjgDm;
	}

	public void setSwjgDm(String swjgDm) {
		this.swjgDm = swjgDm;
	}

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}

	public String getQqfs() {
		return qqfs;
	}

	public void setQqfs(String qqfs) {
		this.qqfs = qqfs;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSwjgMc() {
		return swjgMc;
	}

	public void setSwjgMc(String swjgMc) {
		this.swjgMc = swjgMc;
	}

	@Override
	public String toString() {
		return toStringHelper(this).
				add("id", id).
				add("url", url).
				add("swjgDm", swjgDm).
				add("yxbz", yxbz).
				add("qqfs", qqfs).
				add("method", method).
				add("username", username).
				add("pwd", pwd).
				add("swjgMc", swjgMc).
				add("dsptbm", dsptbm).
				toString();
	}
}
