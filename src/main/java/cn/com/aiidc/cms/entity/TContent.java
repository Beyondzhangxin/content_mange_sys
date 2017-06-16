package cn.com.aiidc.cms.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_content")
public class TContent implements java.io.Serializable {

	private static final long serialVersionUID = -1611312821607287318L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="content_id",unique = true, nullable = false)
	private Integer contentId;
	@Column(name = "content_title")
	private String contentTitle;
	@Column(name = "content_small_title")
	private String contentSmallTitle;
	@Column(name = "template_id")
	private Integer templateId;
	@Column(name = "attribute")
	private String attribute;
	@Column(name = "keyword")
	private String keyword;
	@Column(name = "is_review")
	private Integer isReview;//1 不需要审核 	2 需要审核（未审核） 3 审核通过  4 审核未通过
	@Column(name = "is_top")
	private Integer isTop;//1  不需要置顶  2 需要置顶
	@Column(name = "is_sign")
	private Integer isSign;//1 不需要签收  2 需要签收
	@Column(name = "content_describe")
	private String contentDescribe;
	@Column(name = "content_create_user_name")
	private String contentCreateUserName;
	@Temporal(TemporalType.DATE)
	@Column(name = "content_create_time")
	private Date contentCreateTime;
	@Column(name = "content_file_name")
	private String contentFileName;
	@Column(name = "content_file_path")
	private String contentFilePath;
	@Column(name = "content_file_url")
	private String  contentFileUrl;
	@Column(name="is_display")
	private Integer isDisplay;// 1 显示  2 回收站

	public TContent() {
		
	}
	
	public TContent(Integer contentId, String contentTitle, String contentSmallTitle, Integer templateId,
			String attribute, String keyword, Integer isReview, Integer isTop, Integer isSign, String contentDescribe,
			String contentCreateUserName, Date contentCreateTime, String contentFileName, String contentFilePath,
			String contentFileUrl, Integer isDisplay) {
		super();
		this.contentId = contentId;
		this.contentTitle = contentTitle;
		this.contentSmallTitle = contentSmallTitle;
		this.templateId = templateId;
		this.attribute = attribute;
		this.keyword = keyword;
		this.isReview = isReview;
		this.isTop = isTop;
		this.isSign = isSign;
		this.contentDescribe = contentDescribe;
		this.contentCreateUserName = contentCreateUserName;
		this.contentCreateTime = contentCreateTime;
		this.contentFileName = contentFileName;
		this.contentFilePath = contentFilePath;
		this.contentFileUrl = contentFileUrl;
		this.isDisplay = isDisplay;
	}




	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentSmallTitle() {
		return contentSmallTitle;
	}

	public void setContentSmallTitle(String contentSmallTitle) {
		this.contentSmallTitle = contentSmallTitle;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getIsReview() {
		return isReview;
	}

	public void setIsReview(Integer isReview) {
		this.isReview = isReview;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public String getContentDescribe() {
		return contentDescribe;
	}

	public void setContentDescribe(String contentDescribe) {
		this.contentDescribe = contentDescribe;
	}

	public String getContentCreateUserName() {
		return contentCreateUserName;
	}

	public void setContentCreateUserName(String contentCreateUserName) {
		this.contentCreateUserName = contentCreateUserName;
	}

	public Date getContentCreateTime() {
		return contentCreateTime;
	}

	public void setContentCreateTime(Date contentCreateTime) {
		this.contentCreateTime = contentCreateTime;
	}

	public String getContentFileName() {
		return contentFileName;
	}

	public void setContentFileName(String contentFileName) {
		this.contentFileName = contentFileName;
	}

	public String getContentFilePath() {
		return contentFilePath;
	}

	public void setContentFilePath(String contentFilePath) {
		this.contentFilePath = contentFilePath;
	}

	public String getContentFileUrl() {
		return contentFileUrl;
	}

	public void setContentFileUrl(String contentFileUrl) {
		this.contentFileUrl = contentFileUrl;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	
	
}
