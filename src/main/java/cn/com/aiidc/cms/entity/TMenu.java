package cn.com.aiidc.cms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_MENU")
public class TMenu implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3368117662040158599L;
	
	@Column(name="id")
	@Id
	private int id;
	@Column(name="text")
	private String text;
	@Column(name="iconcls")
	private String iconCls;
	@Column(name="funcid")
	private String funcid;
	@Column(name="action")
	private String action;
	@Column(name="pid")
	private String pid;
	
	@OneToMany(targetEntity=TMenu.class,mappedBy="pid", fetch=FetchType.LAZY)
	
	private List<TMenu> children = new ArrayList<TMenu>();
	
	public int getId() {
		return id;
	}
	public String getText() {
		return text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public String getFuncid() {
		return funcid;
	}
	public String getAction() {
		return action;
	}
	public String getPid() {
		return pid;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public void setFuncid(String funcid) {
		this.funcid = funcid;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
	public List<TMenu> getChildren()
	{
		return children;
	}

}
