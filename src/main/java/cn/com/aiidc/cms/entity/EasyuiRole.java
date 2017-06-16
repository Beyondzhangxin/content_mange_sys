package cn.com.aiidc.cms.entity;

import java.util.List;

public class EasyuiRole {
	private long total;
	List<TRole> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<TRole> getRows() {
		return rows;
	}
	public void setRows(List<TRole> rows) {
		this.rows = rows;
	}
	
}
