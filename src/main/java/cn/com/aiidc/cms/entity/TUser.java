package cn.com.aiidc.cms.entity;
// Generated 2017-1-24 16:15:16 by Hibernate Tools 5.1.0.Final

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TUser generated by hbm2java
 */

@Entity
@Table(name = "t_user", catalog = "aiidc_cms")
public class TUser implements UserDetails, java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private TDepartment TDepartment;
	private TRole TRole;
	private String username;
	private String userRealname;
	private String gender;
	private Date birthDate;
	private Integer mobilePhoneNum;
	private String emailAddr;
	private String password;
	private Date userCrtDate;
	private Date lastLogDate;
	private Date expireDate;
	private String lastLogIp;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private boolean isDisplay;
	private String position;
	
	private Set<TUserRole> userRoles = new HashSet<TUserRole>(0);
	private List<GrantedAuthority>  authorities;
	
	public TUser() {
	}
	public TUser(Integer userId) {
		this.userId = userId;
	}

	/** minimal constructor */
	public TUser(Integer userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
	public TUser(TDepartment TDepartment, TRole TRole, String username, String userRealname, String gender,
			Date birthDate, Integer mobilePhoneNum, String emailAddr, String password, Date userCrtDate,
			Date lastLogDate, Date expireDate, String lastLogIp, Boolean isExpired, Boolean isLocked,
			Boolean isValidate, Boolean isDisplay, String position,Set<TUserRole> userRoles) {
		this.TDepartment = TDepartment;
		this.TRole = TRole;
		this.username = username;
		this.userRealname = userRealname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.mobilePhoneNum = mobilePhoneNum;
		this.emailAddr = emailAddr;
		this.password = password;
		this.userCrtDate = userCrtDate;
		this.lastLogDate = lastLogDate;
		this.expireDate = expireDate;
		this.lastLogIp = lastLogIp;
		this.accountExpired = isExpired;
		this.accountLocked = isLocked;
		this.credentialsExpired = isValidate;
		this.isDisplay = isDisplay;
		this.position = position;
		this.userRoles = userRoles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dep_id")
	public TDepartment getTDepartment() {
		return this.TDepartment;
	}
	public void setTDepartment(TDepartment TDepartment) {
		this.TDepartment = TDepartment;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	public TRole getTRole() {
		return this.TRole;
	}
	public void setTRole(TRole TRole) {
		this.TRole = TRole;
	}

	@Column(name = "user_name", length = 12)
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}

	@Column(name = "user_realname", length = 12)
	public String getUserRealname() {
		return this.userRealname;
	}
	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	@Column(name = "gender", length = 1)
	public String getGender() {
		return this.gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthDate", length = 19)
	public Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "mobilePhoneNum")
	public Integer getMobilePhoneNum() {
		return this.mobilePhoneNum;
	}
	public void setMobilePhoneNum(Integer mobilePhoneNum) {
		this.mobilePhoneNum = mobilePhoneNum;
	}

	@Column(name = "emailAddr", length = 50)
	public String getEmailAddr() {
		return this.emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "user_crtDate", length = 19)
	public Date getUserCrtDate() {
		return this.userCrtDate;
	}
	public void setUserCrtDate(Date userCrtDate) {
		this.userCrtDate = userCrtDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastLogDate", length = 19)
	public Date getLastLogDate() {
		return this.lastLogDate;
	}
	public void setLastLogDate(Date lastLogDate) {
		this.lastLogDate = lastLogDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expireDate", length = 19)
	public Date getExpireDate() {
		return this.expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Column(name = "lastLogIp", length = 50)
	public String getLastLogIp() {
		return this.lastLogIp;
	}
	public void setLastLogIp(String lastLogIp) {
		this.lastLogIp = lastLogIp;
	}
	
	@JsonProperty("accountExpired")
	@Column(name = "isExpired")
	public boolean getAccountExpired() {
		return this.accountExpired;
	}
	@JsonProperty("accountExpired")
	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	@JsonProperty("accountLocked")
	@Column(name = "isLocked")
	public boolean getAccountLocked() {
		return this.accountLocked;
	}
	@JsonProperty("accountLocked")
	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	@JsonProperty("credentialsExpired")
	@Column(name = "isValidate")
	public boolean getCredentialsExpired() {
		return this.credentialsExpired;
	}
	@JsonProperty("credentialsExpired")
	public void setCredentialsExpired(Boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}
	
	@JsonProperty("isDisplay")
	@Column(name = "isDisplay")
	public boolean getIsDisplay() {
		return this.isDisplay;
	}
	@JsonProperty("isDisplay")
	public void setIsDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	
	@Column(name = "position", length = 12)
	public String getPosition() {
		return this.position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	@Transient    //如果数据库中没有相应字段，则要加上这个注解
	public List<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUser")
	@Transient
	public Set<TUserRole> getUserRoles() {
		return this.userRoles;
	}
	public void setUserRoles(Set<TUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return !this.accountExpired;
		
	}
	public void setAccountNonExpired(boolean isAccountNonExpired){
		this.accountExpired = !isAccountNonExpired;
	}
	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return !this.accountLocked;
	}
	public void setAccountNonLocked(boolean isAccountNonLocked){
		this.accountExpired = !isAccountNonLocked;
	}
	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return this.credentialsExpired;
	}
	public void setCredentialsNonExpired(boolean isCredentialsNonExpired){
		this.credentialsExpired = !isCredentialsNonExpired;
	}
	@Override
	@Transient
	public boolean isEnabled() {
		return this.isDisplay;
	}
	public void setEnabled(boolean enabled){
		this.isDisplay = enabled;
	}
}
