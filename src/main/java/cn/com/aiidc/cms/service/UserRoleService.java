package cn.com.aiidc.cms.service;

import cn.com.aiidc.cms.dao.RoleDao;
import cn.com.aiidc.cms.dao.UserRoleDao;
import cn.com.aiidc.cms.dao.Userdao;
import cn.com.aiidc.cms.entity.TRole;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.entity.TUserRole;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangx on 2017/4/21 at 16:19.
 */
@Service
public class UserRoleService
{
    @Resource
    UserRoleDao userRoleDao;
    @Resource
    Userdao userdao;
    @Resource
    RoleDao roleDao;

    public RoleDao getRoleDao()
    {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao)
    {
        this.roleDao = roleDao;
    }

    public Userdao getUserdao()
    {
        return userdao;
    }

    public void setUserdao(Userdao userdao)
    {
        this.userdao = userdao;
    }

    public UserRoleDao getUserRoleDao()
    {
        return userRoleDao;
    }

    public void setUserRoleDao(UserRoleDao userRoleDao)
    {
        this.userRoleDao = userRoleDao;
    }

    public List<TRole> getRolelistbyUserId(int id)
    {
        List<TUserRole> list=getUserRolelistByUserId(id);
        List<TRole> roleList=new ArrayList<>();
        for (TUserRole tUserRole : list)
        {
            roleList.add(tUserRole.getTRole());
        }

        return roleList;
    }

    public List<TRole> getUnselectedRolesByUserId(int id){
        List<TRole> listall=roleDao.findAll(TRole.class);
        List<TRole> listin=getRolelistbyUserId(id);
        listall.removeAll(listin);
        return listall;
    }

    public List<TUserRole> getUserRolelistByUserId(int id){
        TUser tUser=userdao.findById(id,TUser.class);
         DetachedCriteria dc=DetachedCriteria.forClass(TUserRole.class);
         dc.add(Restrictions.eq("TUser",tUser));
         List<TUserRole> list=userRoleDao.findByCriteria(dc);
         return list;
    }
    public int deleteAll(List<?> list){
       return userRoleDao.deleteAll(list);
    }
    public  void  save(int userId, int roleId){
        TUser tUser=userdao.findById(userId,TUser.class);
        TRole tRole=roleDao.findById(roleId,TRole.class);
        TUserRole tUserRole=new TUserRole();
        tUserRole.setTUser(tUser);
        tUserRole.setTRole(tRole);
        userRoleDao.saveOrUpdate(tUserRole);
    }

}
