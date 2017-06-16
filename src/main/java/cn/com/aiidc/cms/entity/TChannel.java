package cn.com.aiidc.cms.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_channel")
public class TChannel implements java.io.Serializable {

	private static final long serialVersionUID = -4383309204339922772L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "channel_id", unique = true, nullable = false)
	private Integer channelId;
	@Column(name = "channel_name")
	private String channelName;
	@Column(name = "is_display")
	private Integer isDisplay;//0 伪删除   1 显示
	@Column(name = "site_id")
	private Integer siteId;
	@Column(name = "create_user_id")
	private Integer createUserId;
	@Column(name = "create_time")
	private String createTime;
	@Column(name = "channel_appr")
	private Integer channelAppr;// 0 未审核  1 审核通过  2 审核未通过
	@Column(name = "appr_user_id")
	private Integer appUserId;
	@Column(name = "no_pass_reason")
	private String noPassReason;
	@Column(name = "channel_file_name")
	private String channelFileName;
	@Column(name = "channel_file_path")
	private String channelFilePath;

	public TChannel() {
		
	}

	public TChannel(Integer channelId, String channelName, Integer isDisplay, Integer siteId, Integer createUserId,
			String createTime, Integer channelAppr, Integer appUserId, String noPassReason, String channelFileName,
			String channelFilePath) {
		super();
		this.channelId = channelId;
		this.channelName = channelName;
		this.isDisplay = isDisplay;
		this.siteId = siteId;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.channelAppr = channelAppr;
		this.appUserId = appUserId;
		this.noPassReason = noPassReason;
		this.channelFileName = channelFileName;
		this.channelFilePath = channelFilePath;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getChannelAppr() {
		return channelAppr;
	}

	public void setChannelAppr(Integer channelAppr) {
		this.channelAppr = channelAppr;
	}

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public String getNoPassReason() {
		return noPassReason;
	}

	public void setNoPassReason(String noPassReason) {
		this.noPassReason = noPassReason;
	}

	public String getChannelFileName() {
		return channelFileName;
	}

	public void setChannelFileName(String channelFileName) {
		this.channelFileName = channelFileName;
	}

	public String getChannelFilePath() {
		return channelFilePath;
	}

	public void setChannelFilePath(String channelFilePath) {
		this.channelFilePath = channelFilePath;
	}
}
