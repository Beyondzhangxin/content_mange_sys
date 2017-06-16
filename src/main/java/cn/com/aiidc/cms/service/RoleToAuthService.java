package cn.com.aiidc.cms.service;

import cn.com.aiidc.cms.dao.AuthoritiesDao;
import cn.com.aiidc.cms.dao.RoleDao;
import cn.com.aiidc.cms.dao.RolesAuthoritiyDao;
import cn.com.aiidc.cms.entity.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangx on 2017/4/6 at 21:55.
 */
@Service
public class RoleToAuthService
{
    @Resource
    private RolesAuthoritiyDao rolesAuthoritiyDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private AuthoritiesDao authoritiesDao;

    public AuthoritiesDao getAuthoritiesDao()
    {
        return authoritiesDao;
    }

    public void setAuthoritiesDao(AuthoritiesDao authoritiesDao)
    {
        this.authoritiesDao = authoritiesDao;
    }

    public RoleDao getRoleDao()
    {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao)
    {
        this.roleDao = roleDao;
    }

    public RolesAuthoritiyDao getRolesAuthoritiyDao()
    {
        return rolesAuthoritiyDao;
    }

    public void setRolesAuthoritiyDao(RolesAuthoritiyDao rolesAuthoritiyDao)
    {
        this.rolesAuthoritiyDao = rolesAuthoritiyDao;
    }

    public List<TAuthorities> getUnselectedAuthByRoleId(int id)
    {
        List<TAuthorities> listall = authoritiesDao.findAll(TAuthorities.class);
        List<TAuthorities> listin = getAuthlistByRoleId(id);
        listall.removeAll(listin);
        return listall;
    }




    public List<TAuthorities> getAuthlistByRoleId(int id)
    {
        TRole role = roleDao.findById(id,TRole.class);
        DetachedCriteria dc = DetachedCriteria.forClass(TRolesAuthorities.class);
        dc.add(Restrictions.eq("TRole", role));
        List<TRolesAuthorities> list=rolesAuthoritiyDao.findByCriteria(dc);
        List<TAuthorities> authlist = new ArrayList<>();
        for (TRolesAuthorities tRolesAuthorities : list)
        {
            authlist.add(tRolesAuthorities.getTAuthorities());
        }
        return authlist;

    }

    public List<TRolesAuthorities> getrestoauthlistByRoleid(int id)
    {
        TRole tRole = roleDao.findById(id, TRole.class);
        DetachedCriteria dc = DetachedCriteria.forClass(TRolesAuthorities.class);
        dc.add(Restrictions.eq("TRole", tRole));
        List<TRolesAuthorities> list = rolesAuthoritiyDao.findByCriteria(dc);
        return list;
    }

    public void save(int resId, int authId)
    {
        TRole tRole = roleDao.findById(resId, TRole.class);
        TAuthorities tAuthorities = authoritiesDao.findById(authId, TAuthorities.class);
        TRolesAuthorities tRolesAuthorities=new TRolesAuthorities();
        tRolesAuthorities.setTRole(tRole);
        tRolesAuthorities.setTAuthorities(tAuthorities);
        rolesAuthoritiyDao.saveOrUpdate(tRolesAuthorities);

    }


    public int deleteAll(List<?> list)
    {
        return rolesAuthoritiyDao.deleteAll(list);
    }

}
