package com.aisino.web.util;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {
	private static final long serialVersionUID = 1L;
	private int pageSize;
	private int pageNo = 1;
	private List<?> data;
	private int totalRows;

	public Page(int pageNo) {
		this.pageNo = pageNo;
		this.pageSize = Constants.getDefaultPageSize();
	}

	public Page(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return this.totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		if (this.totalRows % this.pageSize == 0) {
			return this.totalRows / this.pageSize;
		}
		return this.totalRows / this.pageSize + 1;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public boolean hasNextPage() {
		return getPageNo() < getTotalPages() - 1;
	}

	public boolean hasPreviousPage() {
		return getPageNo() > 1;
	}

	public int getStartRow() {
		return (this.pageNo - 1) * this.pageSize;
	}

	public int getEndRow() {
		return this.pageNo * this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getData() {
		return this.data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}