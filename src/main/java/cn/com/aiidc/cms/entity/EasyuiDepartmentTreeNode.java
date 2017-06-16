package cn.com.aiidc.cms.entity;

import java.util.ArrayList;
import java.util.List;

public class EasyuiDepartmentTreeNode {
	private Integer id;
	private String text;
	private List<EasyuiDepartmentTreeNode> children=new ArrayList<EasyuiDepartmentTreeNode>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<EasyuiDepartmentTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<EasyuiDepartmentTreeNode> children) {
		this.children = children;
	}

}
