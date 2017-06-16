/**
 *该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 *该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 * @author joyu
 *@date 2017/03/22
 */
package cn.com.aiidc.common.security;

import cn.com.aiidc.cms.dao.Userdao;
import cn.com.aiidc.cms.entity.TUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private Userdao usersDao;

	@Autowired
	private UserCache userCache;
	
	@Autowired
	private MessageSource messageSource;
	
	private boolean useCache = false;
	
	protected static final Log logger = LogFactory.getLog(UserDetailsService.class);
	
	/**
     *  登陆验证时，通过username获取用户的所有权限信息，
     *   并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
	
		
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		TUser user = null;
		if(this.useCache){
			user = (TUser) this.userCache.getUserFromCache(username);
		}
		if(user == null){
			//根据用户名取得一个SysUsers对象，以获取该用户的其他信息
			user = this.usersDao.getByUsername(username);
			if(user == null)
				throw new UsernameNotFoundException(this.messageSource.getMessage(
						"UserDetailsService.userNotFount", new Object[]{username}, null));
			//得到用户的权限
			auths = this.usersDao.loadUserAuthorities( username );
			
			user.setAuthorities(auths);
		}

/*		logger.info("*********************"+username+"***********************");
		logger.info(user.getAuthorities());
		logger.info("********************************************************");*/
		
		this.userCache.putUserInCache(user);
		
		return user;
	}

	
	//设置用户缓存功能。
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }
    
   
	
}
