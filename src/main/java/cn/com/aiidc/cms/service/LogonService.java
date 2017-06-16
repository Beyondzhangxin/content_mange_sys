package cn.com.aiidc.cms.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.HibernateDao;
import cn.com.aiidc.cms.dao.Userdao;
import cn.com.aiidc.cms.entity.TMenu;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.security.CustomInvocationSecurityMetadataSourceService;


@Service
public class LogonService {
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private Userdao userdao;
	public HibernateDao getHibernateDao() 
	{
		return hibernateDao;
	}

	public void setHibernateDao(HibernateDao hibernateDao) 
	{
		this.hibernateDao = hibernateDao;
	}

	
	public List<TMenu> getMenuTree()
	{
		DetachedCriteria dc = DetachedCriteria.forClass(TMenu.class);
		dc.add(Restrictions.isNull("pid"));
		return hibernateDao.findByCriteria(dc, TMenu.class);
	}
	
	/**
	 * 
	 * <p>Description:通过取得用户拥有的菜单权限，构建菜单树 </p>
	 * @author joyu
	 * @return
	 * @throws Exception
	 */
	public JSONArray getMenuTreeForJson(String username)
	throws Exception
	{
		//取得系统所有菜单
		List<TMenu> menus = getMenuTree();
		JSONArray jarr = new JSONArray();
	    //List<GrantedAuthority> auths =(List<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		//取得当前用户拥有的菜单权限
		List userMenu = userdao.getUserHasAuthorityMenu(username);
		
		/**
		 * 把用户没有的菜单从系统菜单中移除，剩下的是用户拥有的菜单，并构建菜单的json格式数据
		 * 此处用了一个开源的BeanUtils，beanUtils会给传入的bean对象进行解析生成json，如果对象中
		 * 还有子对象它会递归解析子对象，最终生成json格式数据。
		 * 下面注释代码请不要删除，有参考价值（另一种处理方式，但在本系统中不太合适）
		 */
		for(TMenu menu : menus){
			//Collection<ConfigAttribute> getAttributes = csmss.getAttributes(menu.getText());
	/*		if(auths.contains(new SimpleGrantedAuthority("AUTH_ADMIN"))|auths.contains(new SimpleGrantedAuthority("AUTH_SET"))){
					jarr.put(BeanUtils.describeToJson(menu));
			}*/
/*			if(auths.contains(getAttributes)){
				jarr.put(BeanUtils.describeToJson(menu));
			}*/
			if(userMenu.contains(menu.getText())){
				if(menu.getChildren().size()>0){
			        Iterator it = menu.getChildren().iterator();  
			        while(it.hasNext()){  
			        	TMenu mnu = (TMenu)it.next();  
			            if(!userMenu.contains(mnu.getText())){  
			                //移除当前的对象  
			                it.remove();  
			            }  
			        }  
				}
				jarr.put(BeanUtils.describeToJson(menu));
			}
		}
		return jarr;
	}
}
