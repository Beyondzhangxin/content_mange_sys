package cn.com.aiidc.cms.entity;
// Generated 2017-1-10 17:26:41 by Hibernate Tools 5.1.0.Final

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TRoleRights generated by hbm2java
 */
@Entity
@Table(name = "t_roles_authorities", catalog = "aiidc_cms")
public class TRolesAuthorities implements java.io.Serializable {

	private Integer id;
	private TAuthorities TAuthorities;
	private TRole TRole;

	public TRolesAuthorities() {
	}

	public TRolesAuthorities(TAuthorities TAuthorities, TRole TRole) {
		this.TAuthorities = TAuthorities;
		this.TRole = TRole;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authority_id")
	public TAuthorities getTAuthorities() {
		return this.TAuthorities;
	}
	                                                                                                                                   	
	public void setTAuthorities(TAuthorities TAuthorities) {
		this.TAuthorities = TAuthorities;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	public TRole getTRole() {
		return this.TRole;
	}

	public void setTRole(TRole TRole) {
		this.TRole = TRole;
	}

}