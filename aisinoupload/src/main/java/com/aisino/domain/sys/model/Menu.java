package com.aisino.domain.sys.model;

import com.aisino.domain.base.model.BaseObject;

import java.util.ArrayList;
import java.util.List;

public class Menu extends BaseObject {

	private static final long serialVersionUID = 1L;
	private String menuCode;
	private String menuName;
	private String funcEntry;
	private int menuLevel;
	private String parentCode;
	private String fullCode;
	private int nodeOrder;
	private String isLeaf;
	private Integer isVisible;
	private List<Menu> children = new ArrayList<Menu>();

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getFuncEntry() {
		return funcEntry;
	}

	public void setFuncEntry(String funcEntry) {
		this.funcEntry = funcEntry;
	}

	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getFullCode() {
		return fullCode;
	}

	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}

	public int getNodeOrder() {
		return nodeOrder;
	}

	public void setNodeOrder(int nodeOrder) {
		this.nodeOrder = nodeOrder;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public void addChild(Menu menu) {
		children.add(menu);
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
}
