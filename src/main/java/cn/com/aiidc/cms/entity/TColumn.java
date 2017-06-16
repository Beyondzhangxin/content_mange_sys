package cn.com.aiidc.cms.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "t_column")
public class TColumn implements java.io.Serializable {

	private static final long serialVersionUID = -5651095084261652502L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="column_id",unique = true, nullable = false)
	private Integer columnId;
	@Column(name="column_name")
	private String columnName;
	@Column(name="site_id")
	private Integer siteId;
	@Column(name="channel_id")
	private Integer channelId;
	@Column(name="template_id")
	private Integer templateId;
	@Column(name="read_power")
	private Integer readPower;
	@Column(name="parent_id")
	private Integer parentId;
	@Column(name="is_display")
	private Integer isDisplay;
	@Column(name="column_create_time")
	private String columnCreateTime;
	@Column(name="column_create_user_id")
	private Integer columnCreateUserId;
	@Column(name="column_appr")
	private Integer columnAppr;
	@Column(name="column_appr_user_id")
	private Integer columnApprUserId;
	@Column(name="no_pass_reason")
	private String noPassReason;
	@Column(name="column_file_name")
	private String columnFileName;
	@Column(name="column_file_path")
	private String columnFilePath;
	
	@OneToMany(targetEntity=TColumn.class,mappedBy="parentId", fetch=FetchType.LAZY)
	@Where(clause="column_appr=1")
	private List<TColumn> children = new ArrayList<TColumn>();

	public TColumn() {
		
	}

	public TColumn(Integer columnId, String columnName, Integer siteId, Integer channelId, Integer templateId,
			Integer readPower, Integer parentId, Integer isDisplay, String columnCreateTime, Integer columnCreateUserId,
			Integer columnAppr, Integer columnApprUserId, String noPassReason, String columnFileName,
			String columnFilePath, List<TColumn> children) {
		super();
		this.columnId = columnId;
		this.columnName = columnName;
		this.siteId = siteId;
		this.channelId = channelId;
		this.templateId = templateId;
		this.readPower = readPower;
		this.parentId = parentId;
		this.isDisplay = isDisplay;
		this.columnCreateTime = columnCreateTime;
		this.columnCreateUserId = columnCreateUserId;
		this.columnAppr = columnAppr;
		this.columnApprUserId = columnApprUserId;
		this.noPassReason = noPassReason;
		this.columnFileName = columnFileName;
		this.columnFilePath = columnFilePath;
		this.children = children;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getReadPower() {
		return readPower;
	}

	public void setReadPower(Integer readPower) {
		this.readPower = readPower;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getColumnCreateTime() {
		return columnCreateTime;
	}

	public void setColumnCreateTime(String columnCreateTime) {
		this.columnCreateTime = columnCreateTime;
	}

	public Integer getColumnCreateUserId() {
		return columnCreateUserId;
	}

	public void setColumnCreateUserId(Integer columnCreateUserId) {
		this.columnCreateUserId = columnCreateUserId;
	}

	public Integer getColumnAppr() {
		return columnAppr;
	}

	public void setColumnAppr(Integer columnAppr) {
		this.columnAppr = columnAppr;
	}

	public Integer getColumnApprUserId() {
		return columnApprUserId;
	}

	public void setColumnApprUserId(Integer columnApprUserId) {
		this.columnApprUserId = columnApprUserId;
	}

	public String getNoPassReason() {
		return noPassReason;
	}

	public void setNoPassReason(String noPassReason) {
		this.noPassReason = noPassReason;
	}

	public String getColumnFileName() {
		return columnFileName;
	}

	public void setColumnFileName(String columnFileName) {
		this.columnFileName = columnFileName;
	}

	public String getColumnFilePath() {
		return columnFilePath;
	}

	public void setColumnFilePath(String columnFilePath) {
		this.columnFilePath = columnFilePath;
	}

	public List<TColumn> getChildren() {
		return children;
	}

	public void setChildren(List<TColumn> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "TColumn [columnId=" + columnId + ", columnName=" + columnName + ", siteId=" + siteId + ", channelId="
				+ channelId + ", templateId=" + templateId + ", readPower=" + readPower + ", parentId=" + parentId
				+ ", isDisplay=" + isDisplay + ", columnCreateTime=" + columnCreateTime + ", columnCreateUserId="
				+ columnCreateUserId + ", columnAppr=" + columnAppr + ", columnApprUserId=" + columnApprUserId
				+ ", noPassReason=" + noPassReason + ", columnFileName=" + columnFileName + ", columnFilePath="
				+ columnFilePath + ", children=" + children + "]";
	}
	
}
