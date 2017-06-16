package cn.com.aiidc.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_site")
public class TSite implements java.io.Serializable {

	private static final long serialVersionUID = -1175571724140801252L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "site_id", unique = true, nullable = false)
	private Integer siteId;
	@Column(name = "is_display")
	private Integer isDisplay;
	@Column(name = "site_name")
	private String siteName;
	@Column(name = "site_url")
	private String siteUrl;
	@Column(name = "up_application")
	private String upApplication;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "page_code")
	private String pageCode;
	@Column(name = "key_word")
	private String keyWord;
	@Column(name = "remarks_msg")
	private String remarksMsg;
	@Column(name = "admin_email")
	private String adminEmail;
	@Column(name = "create_time")
	private String createTime;
	@Column(name = "site_path")
	private String sitePath;
	@Column(name = "site_appr")
	private Integer siteAppr;//0 未审核  1 审核通过  2 审核未通过
	@Column(name = "appr_user_id")
	private Integer apprUserId;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name="appr_reason")
	private String apprReason;
	
	
	public TSite() {
		
	}
	
	public TSite(Integer siteId, Integer isDisplay, String siteName, String siteUrl, String upApplication,
			String fileName, String pageCode, String keyWord, String remarksMsg, String adminEmail, String createTime,
			String sitePath, Integer siteAppr, Integer apprUserId, Integer userId, String apprReason) {
		super();
		this.siteId = siteId;
		this.isDisplay = isDisplay;
		this.siteName = siteName;
		this.siteUrl = siteUrl;
		this.upApplication = upApplication;
		this.fileName = fileName;
		this.pageCode = pageCode;
		this.keyWord = keyWord;
		this.remarksMsg = remarksMsg;
		this.adminEmail = adminEmail;
		this.createTime = createTime;
		this.sitePath = sitePath;
		this.siteAppr = siteAppr;
		this.apprUserId = apprUserId;
		this.userId = userId;
		this.apprReason = apprReason;
	}


	public Integer getSiteId() {
		return siteId;
	}
	
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}


	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}


	public String getSiteName() {
		return siteName;
	}


	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}


	public String getSiteUrl() {
		return siteUrl;
	}


	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}


	public String getUpApplication() {
		return upApplication;
	}


	public void setUpApplication(String upApplication) {
		this.upApplication = upApplication;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getPageCode() {
		return pageCode;
	}


	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}


	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}


	public String getRemarksMsg() {
		return remarksMsg;
	}


	public void setRemarksMsg(String remarksMsg) {
		this.remarksMsg = remarksMsg;
	}


	public String getAdminEmail() {
		return adminEmail;
	}


	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getSitePath() {
		return sitePath;
	}


	public void setSitePath(String sitePath) {
		this.sitePath = sitePath;
	}


	public Integer getSiteAppr() {
		return siteAppr;
	}


	public void setSiteAppr(Integer siteAppr) {
		this.siteAppr = siteAppr;
	}


	public Integer getApprUserId() {
		return apprUserId;
	}


	public void setApprUserId(Integer apprUserId) {
		this.apprUserId = apprUserId;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getApprReason() {
		return apprReason;
	}

	public void setApprReason(String apprReason) {
		this.apprReason = apprReason;
	}
	
	
}
