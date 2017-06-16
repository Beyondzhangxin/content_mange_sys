package cn.com.aiidc.cms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.aiidc.cms.entity.TSite;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.SiteMgrService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.DateUtil;

@Controller
@RequestMapping("/sitemgr")
public class SiteMgrController {
	
	@Resource
	private SiteMgrService siteMgrService;
	
	@Resource
	private UserService userService;
	
	public SiteMgrService getSiteMgrService() {
		return siteMgrService;
	}

	public void setSiteMgrService(SiteMgrService siteMgrService) {
		this.siteMgrService = siteMgrService;
	}

	private Map<String,Object> map = new HashMap<String,Object>();
	
	@RequestMapping(value="list")
	public String list() throws Exception{
		map.put("siteName", null);
		map.put("siteAppr", 2);//0 未审核  1 审核通过  2 审核未通过
		return "sitemgr/list";
	}
	
	/**
	 * 添加站点
	 * @param res
	 * @param req
	 */
	@RequestMapping(value="addSite",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addSite(HttpServletResponse res,HttpServletRequest req)
	{
		JSONObject json = new JSONObject();
		try {
			TSite site = new TSite();
			site.setIsDisplay(1);//1表示显示  0 表示删除
			String siteName = req.getParameter("siteName");
			//校验该站点是否已存在
			Integer siteId =-1;
			List<TSite> list = siteMgrService.findSiteByName(siteName,siteId);
			if( list.size() != 0  || siteName==null ){
				json.put("success", false);
				json.put("message","站点名称已存在!");
				return json.toString();
			}
			site.setSiteName(siteName);
			String siteUrl = req.getParameter("siteUrl");
			List<TSite> list1= siteMgrService.findSiteByUrl(siteUrl, siteId);
			if( list1.size() != 0  || siteUrl == null){
				json.put("success", false);
				json.put("message","站点地址已存在!");
				return json.toString();
			}
			site.setSiteUrl(siteUrl);
			String upApplication = req.getParameter("upApplication");
			site.setUpApplication(upApplication);
			String pageCode = req.getParameter("pageCode");
			site.setPageCode(pageCode);
			String keyWord = req.getParameter("keyWord");
			site.setKeyWord(keyWord);
			String remarksMsg = req.getParameter("remarksMsg");
			site.setRemarksMsg(remarksMsg);
			String adminEmail = req.getParameter("adminEmail");
			if(adminEmail ==null ){
				json.put("success", false);
				json.put("message","管理员邮箱不能为空!");
				return json.toString();
			}
			site.setAdminEmail(adminEmail);
			Date date = new Date();
			String createTime = DateUtil.getStringByDate(date, "yyyy-MM-dd");
			site.setCreateTime(createTime);
			//刚刚创建的站点，默认审核状态为：未审核
			site.setSiteAppr(0);//0 未审核  1 审核通过  2 审核未通过
			//获取当前用户的信息
			TUser user = userService.getLoginUser();
			site.setUserId(user.getUserId());
			//保存站点
			siteId = siteMgrService.addSite(site);
			if(siteId == -1){
				json.put("success", false);
				json.put("message","添加站点失败!");
				return json.toString();
			}else{
				json.put("success", true);
				json.put("message","添加站点成功!");
				return json.toString();
			}
		} catch (Exception e) {
			json.put("success", false);
			json.put("message","添加站点失败!");
			return json.toString();
		}
	}
	
	
	/**
	 * 站点编辑
	 * 编辑站点时，需要注意：审核通过后的站点不能再被编辑；编辑后的站点需要再次被审核。
	 * @param siteId
	 * @param siteName
	 * @param siteUrl
	 * @param upApplication
	 * @param pageCode
	 * @param keyWord
	 * @param remarksMsg
	 * @param adminEmail
	 * @return
	 */
	@RequestMapping(value="editSite",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String editSite(@RequestParam("siteId") Integer siteId,
			@RequestParam("siteName") String siteName,
			@RequestParam("siteUrl") String siteUrl,
			@RequestParam("upApplication") String upApplication,
			@RequestParam("pageCode") String pageCode,
			@RequestParam("keyWord") String keyWord,
			@RequestParam("remarksMsg") String remarksMsg,
			@RequestParam("adminEmail") String adminEmail,
			HttpServletResponse res)
	{
		JSONObject json = new JSONObject();
		try {
			//判断站点id是否为空
			if(siteId == null ){
				json.put("success", false);
				json.put("message","编辑站点时，站点id不能为空!");
				return json.toString();
			}
			//判断该站点是否为审核通过的站点：如果审核通过了，则该站点不能被编辑
			TSite site = siteMgrService.findSiteBySiteId(siteId);
			if(site.getSiteAppr() == 1){//0 未审核  1 审核通过  2 审核未通过
				json.put("success", false);
				json.put("message","该站点为审核通过后的站点，不能再被编辑!");
				return json.toString();
			}
			site.setSiteId(siteId);
			site.setIsDisplay(1);//1表示显示  0 表示删除
			//校验该站点是否已存在
			List<TSite> list = siteMgrService.findSiteByName(siteName,siteId);
			if( list.size() != 0  || siteName ==null){
				json.put("success", false);
				json.put("message","站点名称已存在!");
				return json.toString();
			}
			site.setSiteName(siteName);
			List<TSite> list1= siteMgrService.findSiteByUrl(siteUrl, siteId);
			if( list1.size() != 0  || siteUrl == null){	
				json.put("success", false);
				json.put("message","站点地址已存在！");
				return json.toString();
			}
			site.setSiteUrl(siteUrl);
			site.setUpApplication(upApplication);
			site.setPageCode(pageCode);
			site.setKeyWord(keyWord);
			site.setRemarksMsg(remarksMsg);
			if(adminEmail ==null ){
				json.put("success", false);
				json.put("message","管理员邮箱不能为空!");
				return json.toString();
			}
			site.setAdminEmail(adminEmail);
			Date date = new Date();
			String createTime = DateUtil.getStringByDate(date, "yyyy-MM-dd");
			site.setCreateTime(createTime);
			//获取当前用户的信息
			TUser user = userService.getLoginUser();
			site.setUserId(user.getUserId());
			//编辑的站点，默认审核状态为：未审核
			site.setSiteAppr(0);//0 未审核  1 审核通过  2 审核未通过
			//更新站点信息
			siteMgrService.editSite(site);
			json.put("success", true);
			json.put("message","编辑站点成功!");
			return json.toString();
		} catch (Exception e) {
			json.put("success", false);
			json.put("message","编辑站点失败!");
			return json.toString();
		} 
	}	
	
	/**
	 * 删除站点:
	 * 删除站点：对于审核通过的站点进行伪删除，即可以保留一段时间；其余的站点直接删除
	 * @param siteId 站点id
	 * @return
	 */
	@RequestMapping(value="deleteSite",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteSite(Integer siteId){
		JSONObject json = new JSONObject();
		//如果是审核通过的站点，改变站点的状态，保留一段时间
		TSite site = siteMgrService.findSiteBySiteId(siteId);
		if(site.getSiteAppr() == 1){//0 未审核  1 审核通过  2 审核未通过
			siteMgrService.deleteByUpdateIsDisplay(site);
			json.put("success", true);
			json.put("message", "伪删除站点成功");
			return json.toString();
		}else{
			siteMgrService.deleteSite(site);
			json.put("success", true);
			json.put("message", "删除站点成功");
			return json.toString();
		}
	}
	
	/**
	 * 满足条件的分页查询
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/listdataBypage",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listdataBypage(int page,int rows){
		map.put("page", page);
		map.put("pageSize", rows);
		List<TSite> list = siteMgrService.findAllSiteByPage(map);
		JSONArray jarr = new JSONArray();
		try {
			for(TSite site : list){
				Map<String,Object> siteMap = BeanUtils.describe(site);
				//创建站点者
				TUser user = userService.getUserById(site.getUserId());
				siteMap.put("userRealname",user.getUserRealname() );
				//审核站点者
				if(site.getApprUserId() !=null ){
					TUser appruser = userService.getUserById(site.getApprUserId());
					siteMap.put("apprUsername",appruser.getUserRealname() );
				}
				jarr.put(siteMap);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		//获取满足要求的记录数
		int total = siteMgrService.queryCount(map);
		//创建返回结果
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	
	/**
	 * 响应三个审核状态按钮的请求
	 * @return
	 */
	@RequestMapping(value="addSiteAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addSiteAppr(Integer siteAppr){
		map.put("siteAppr", siteAppr);
		map.put("siteName", null);
		int page =1;int rows = 10;
		return listdataBypage(page,rows);
	}

	/**
	 * 根据站点名称进行模糊查询
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addQueryCondition",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody()
	public String addQuerySitename (String condition) throws Exception
	{
		map.put("siteName", condition);
		int page =1;int rows = 10;
		return listdataBypage(page,rows);
	}
	

	/**
	 * 查找所有审核通过的站点
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="findAllSite",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findAllSite(){
		List<TSite> list = siteMgrService.findAllSiteBySiteAppr();
		JSONArray jarr = new JSONArray();
		try {
			for(TSite site : list){
				jarr.put(BeanUtils.describeToJson(site));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return jarr.toString();
	}
	
	/**
	 * 根据站点的id，查询站点信息
	 * @param siteId
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/findSiteBySiteId",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findSiteBySiteId(Integer siteId){
		TSite site = siteMgrService.findSiteBySiteId(siteId);
		String  str=null;
		try {
			str =BeanUtils.describeToJson(site).toString();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if(str == null){
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("message", "根据站点id查询失败");
		}
		return str;
	}

}