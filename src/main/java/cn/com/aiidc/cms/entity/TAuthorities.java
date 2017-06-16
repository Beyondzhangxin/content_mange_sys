package cn.com.aiidc.cms.entity;
/**
 * @author joyu
 * @date 2017/03/27
 * Description: 完成authority 的增删改查
 **/

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_authorities", catalog = "aiidc_cms")
public class TAuthorities implements java.io.Serializable
{

    private Integer authorityId;
    private String authorityName;
    private String authorityMark;
    private String authorityDesc;
    private Boolean enable;
    private Boolean isSys;
    private Integer moduleId;
    private Set<TRolesAuthorities> rolesAuthorities = new HashSet<TRolesAuthorities>(0);

    public TAuthorities()
    {
    }

    public TAuthorities(String authorityName, String authorityMark, String authorityDesc, Boolean enable, Boolean isSys,
                        Set<TRolesAuthorities> rolesAuthorities)
    {
        this.authorityName = authorityName;
        this.authorityMark = authorityMark;
        this.authorityDesc = authorityDesc;
        this.enable = enable;
        this.isSys = isSys;
        this.rolesAuthorities = rolesAuthorities;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "authority_id", unique = true, nullable = false)
    public Integer getAuthorityId()
    {
        return this.authorityId;
    }

    public void setAuthorityId(Integer authorityId)
    {
        this.authorityId = authorityId;
    }

    @Column(name = "authority_mark")
    public String getAuthorityMark()
    {
        return authorityMark;
    }

    public void setAuthorityMark(String authorityMark)
    {
        this.authorityMark = authorityMark;
    }

    @Column(name = "authority_name", length = 12)
    public String getAuthorityName()
    {
        return this.authorityName;
    }

    public void setAuthorityName(String authorityName)
    {
        this.authorityName = authorityName;
    }

    @Column(name = "authority_desc", length = 200)
    public String getAuthorityDesc()
    {
        return this.authorityDesc;
    }

    public void setAuthorityDesc(String authorityDesc)
    {
        this.authorityDesc = authorityDesc;
    }

    @Column(name = "enable")
    public Boolean getEnable()
    {
        return this.enable;
    }

    public void setEnable(Boolean enable)
    {
        this.enable = enable;
    }

    @Column(name = "isSys")
    public Boolean getIsSys()
    {
        return this.isSys;
    }

    public void setIsSys(Boolean isSys)
    {
        this.isSys = isSys;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "TAuthorities"   )
    @JsonIgnore
    @Transient
    public Set<TRolesAuthorities> getRolesAuthorities()
    {
        return this.rolesAuthorities;
    }

    public void setRolesAuthorities(Set<TRolesAuthorities> rolesAuthorities)
    {
        this.rolesAuthorities = rolesAuthorities;
    }

    @Column(name = "module_id")
    public Integer getModuleId()
    {
        return moduleId;
    }

    public void setModuleId(Integer moduleId)
    {
        this.moduleId = moduleId;
    }


}
