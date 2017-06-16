package cn.com.aiidc.cms.service;

import cn.com.aiidc.cms.dao.Userdao;
import cn.com.aiidc.cms.entity.TUser;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
	@Resource
	private Userdao userDao;

	public Userdao getUserDao() {
		return userDao;
	}

	public void setUserDao(Userdao userDao) {
		this.userDao = userDao;
	}
 /**
  *  返回当前登录用户的user信息
  * @return
  */
   public TUser getLoginUser(){
	   return userDao.getCurrentUser();
   }
   /**
    * 根据输入用户的userId返回用户的User信息
    * 
    * @param userId
    * @return
    */
   public TUser getUserById(Integer userId){
	   return userDao.getUserById(userId);
   }

   public int getListNum(){
   	 return userDao.queryCountByCriteria(DetachedCriteria.forClass(TUser.class));
   }
}
