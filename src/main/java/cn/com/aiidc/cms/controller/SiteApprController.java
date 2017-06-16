package cn.com.aiidc.cms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.aiidc.cms.entity.TSite;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.SiteApprService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.Chinese2PinYin;
import cn.com.aiidc.common.util.PathUtil;

@Controller
@RequestMapping("/siteappr")
public class SiteApprController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private SiteApprService siteApprService;

	public SiteApprService getSiteApprService() {
		return siteApprService;
	}

	public void setSiteApprService(SiteApprService siteApprService) {
		this.siteApprService = siteApprService;
	}
	
	private Map<String,Object> map = new HashMap<String,Object>();
	
	@RequestMapping(value="list")
	public String list() throws Exception{
		map.put("siteName", null);
		return "siteappr/list";
	}
	
	
	/**
	 * 站点审核的分页管理
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="findSiteForSiteAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	 public String findSiteForSiteAppr(Integer page,Integer rows){
		 map.put("page", page);map.put("pageSize", rows);
		 List<TSite> sites = siteApprService.findAllSiteByPage(map);
		 JSONArray jarr = new JSONArray();
		 try {
			 for(TSite site : sites){
				@SuppressWarnings("unchecked")
				Map<String, Object> sitemap = BeanUtils.describe(site);
				TUser user = userService.getUserById(site.getUserId());
				sitemap.put("userRealname", user.getUserRealname());
				jarr.put(sitemap);
			 }	
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Integer total = siteApprService.queryCount(map);
		//创建返回结果
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	 }
	
	/**
	 * 根据站点名称进行模糊查询
	 * @param siteName
	 * @return
	 */
	@RequestMapping(value="addSiteNameForSiteAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addSiteNameForSiteAppr(String siteName){
		map.put("siteName", siteName);
		Integer page=1;
		Integer rows=10;
		return findSiteForSiteAppr(page,rows);
	}
	
	/**
	 * 保存站点审核结果:
	 * siteAppr = 0 未审核  1 审核通过  2 审核未通过
	 * @param siteId
	 * @param siteAppr
	 * @param apprReason
	 * @return
	 */
	@RequestMapping(value="saveSiteAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String saveSiteAppr(Integer siteId ,Integer siteAppr,String apprReason){
		TSite site = siteApprService.findSiteBySiteId(siteId);
		site.setSiteAppr(siteAppr);
		site.setApprUserId(userService.getLoginUser().getUserId());
		site.setApprReason(apprReason);
		if(siteAppr == 1){//如果审核通过，需要创建站点的文件夹
			String siteFileName = Chinese2PinYin.getPinYinHeadChar(site.getSiteName())+site.getSiteId();//站点文件夹的名字
			site.setFileName(siteFileName);
			String sitePath = PathUtil.getFilePathForDirectory(siteFileName, PathUtil.getServicePath());
			site.setSitePath(sitePath);
		}
		//更新站点信息，保存站点审核结果
		siteApprService.saveSiteApprResult(site);
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("message", "保存站点审核结果成功！");
		return json.toString();
	}
}
