package cn.com.aiidc.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_template_class")
public class TTemplateClass implements java.io.Serializable {

	private static final long serialVersionUID = -5153975759575489260L;
	private Integer templateClassId;
	private Integer templateClass;
	private String tempaleAbstract;

	public TTemplateClass() {
	}

	public TTemplateClass(Integer templateClassId, Integer templateClass, String tempaleAbstract) {
		super();
		this.templateClassId = templateClassId;
		this.templateClass = templateClass;
		this.tempaleAbstract = tempaleAbstract;
	}



	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "template_class_id", unique = true, nullable = false)
	public Integer getTemplateClassId() {
		return this.templateClassId;
	}

	public void setTemplateClassId(Integer templateClassId) {
		this.templateClassId = templateClassId;
	}

	@Column(name = "template_class")
	public Integer getTemplateClass() {
		return this.templateClass;
	}

	public void setTemplateClass(Integer templateClass) {
		this.templateClass = templateClass;
	}

	@Column(name = "tempale_abstract", length = 100)
	public String getTempaleAbstract() {
		return this.tempaleAbstract;
	}

	public void setTempaleAbstract(String tempaleAbstract) {
		this.tempaleAbstract = tempaleAbstract;
	}

}
