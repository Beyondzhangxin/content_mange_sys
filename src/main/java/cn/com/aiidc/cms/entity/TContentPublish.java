package cn.com.aiidc.cms.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_content_publish")
public class TContentPublish implements Serializable {

	private static final long serialVersionUID = -8610422036105261653L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="content_pub_id",unique = true, nullable = false)
	private Integer contentPubId;
	@Column(name="content_id")
	private Integer contentId;
	@Column(name="site_id")
	private Integer siteId;
	@Column(name="channel_id")
	private Integer channelId;
	@Column(name="column_id")
	private Integer columnId;
	@Column(name="content_pub_time")
	private String contentPubTime;
	@Column(name="content_pub_user_name")
	private String contentPubUserName;
	@Column(name="content_pub_path")
	private String contentPubPath;
	@Column(name="content_pub_url")
	private String contentPubUrl;
	@Column(name="content_pub_plan_time")
	private String contentPubPlanTime;
	@Column(name="content_title")
	private String contentTitle;
	
	public TContentPublish() {
		
	}

	public TContentPublish(Integer contentPubId, Integer contentId, Integer siteId, Integer channelId, Integer columnId,
			String contentPubTime, String contentPubUserName, String contentPubPath, String contentPubUrl,
			String contentPubPlanTime, String contentTitle) {
		super();
		this.contentPubId = contentPubId;
		this.contentId = contentId;
		this.siteId = siteId;
		this.channelId = channelId;
		this.columnId = columnId;
		this.contentPubTime = contentPubTime;
		this.contentPubUserName = contentPubUserName;
		this.contentPubPath = contentPubPath;
		this.contentPubUrl = contentPubUrl;
		this.contentPubPlanTime = contentPubPlanTime;
		this.contentTitle = contentTitle;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public Integer getContentPubId() {
		return contentPubId;
	}

	public void setContentPubId(Integer contentPubId) {
		this.contentPubId = contentPubId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
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

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public String getContentPubTime() {
		return contentPubTime;
	}

	public void setContentPubTime(String contentPubTime) {
		this.contentPubTime = contentPubTime;
	}

	public String getContentPubUserName() {
		return contentPubUserName;
	}

	public void setContentPubUserName(String contentPubUserName) {
		this.contentPubUserName = contentPubUserName;
	}

	public String getContentPubPath() {
		return contentPubPath;
	}

	public void setContentPubPath(String contentPubPath) {
		this.contentPubPath = contentPubPath;
	}

	public String getContentPubUrl() {
		return contentPubUrl;
	}

	public void setContentPubUrl(String contentPubUrl) {
		this.contentPubUrl = contentPubUrl;
	}

	public String getContentPubPlanTime() {
		return contentPubPlanTime;
	}

	public void setContentPubPlanTime(String contentPubPlanTime) {
		this.contentPubPlanTime = contentPubPlanTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}


























