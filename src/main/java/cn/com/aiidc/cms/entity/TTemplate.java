package cn.com.aiidc.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_template")
public class TTemplate implements java.io.Serializable {
	private static final long serialVersionUID = 8435987735615584497L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "template_id", unique = true, nullable = false)
	private Integer templateId;
	@Column(name = "template_class")
	private Integer templateClass;
	@Column(name = "template_name")
	private String templateName;
	@Column(name = "template_file_name")
	private String templateFileName;
	@Column(name = "template_file_path")
	private String templateFilePath;
	@Column(name = "template_file_url")
	private String templateFileUrl;

	public TTemplate() {
	}

	public TTemplate(Integer templateId, Integer templateClass, String templateName, String templateFileName,
			String templateFilePath, String templateFileUrl) {
		super();
		this.templateId = templateId;
		this.templateClass = templateClass;
		this.templateName = templateName;
		this.templateFileName = templateFileName;
		this.templateFilePath = templateFilePath;
		this.templateFileUrl = templateFileUrl;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getTemplateClass() {
		return templateClass;
	}

	public void setTemplateClass(Integer templateClass) {
		this.templateClass = templateClass;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

	public String getTemplateFileUrl() {
		return templateFileUrl;
	}

	public void setTemplateFileUrl(String templateFileUrl) {
		this.templateFileUrl = templateFileUrl;
	}


	@Override
	public String toString() {
		return "TTemplate [templateId=" + templateId + ", templateClass=" + templateClass + ", templateName="
				+ templateName + ", templateFileName=" + templateFileName + ", templateFilePath=" + templateFilePath
				+ ", templateFileUrl=" + templateFileUrl + "]";
	}
	
}
