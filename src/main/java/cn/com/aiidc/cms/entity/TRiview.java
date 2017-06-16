package cn.com.aiidc.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_riview")
public class TRiview implements java.io.Serializable {

	private static final long serialVersionUID = -3799431035422193587L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "review_id", unique = true, nullable = false)
	private Integer reviewId;
	@Column(name="content_id")
	private Integer contentId;
	@Column(name="review_status")
	private Integer reviewStatus;
	@Column(name="content_appr_time")
	private String contentApprTime;
	@Column(name="content_appr_user_id")
	private Integer contentApprUserId;
	@Column(name="appr_no_pass_reason")
	private String apprNoPassReason;

	public TRiview() {
		
	}

	public TRiview(Integer reviewId, Integer contentId, Integer reviewStatus, String contentApprTime,
			Integer contentApprUserId, String apprNoPassReason) {
		super();
		this.reviewId = reviewId;
		this.contentId = contentId;
		this.reviewStatus = reviewStatus;
		this.contentApprTime = contentApprTime;
		this.contentApprUserId = contentApprUserId;
		this.apprNoPassReason = apprNoPassReason;
	}


	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getContentApprTime() {
		return contentApprTime;
	}

	public void setContentApprTime(String contentApprTime) {
		this.contentApprTime = contentApprTime;
	}

	public Integer getContentApprUserId() {
		return contentApprUserId;
	}

	public void setContentApprUserId(Integer contentApprUserId) {
		this.contentApprUserId = contentApprUserId;
	}

	public String getApprNoPassReason() {
		return apprNoPassReason;
	}

	public void setApprNoPassReason(String apprNoPassReason) {
		this.apprNoPassReason = apprNoPassReason;
	}
	
	
}

