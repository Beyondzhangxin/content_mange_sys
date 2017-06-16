package cn.com.aiidc.cms.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
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

import cn.com.aiidc.cms.entity.TColumn;
import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TContentPublish;
import cn.com.aiidc.cms.service.ConPubService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.DateUtil;
import cn.com.aiidc.common.util.FileUtil;
import cn.com.aiidc.common.util.PathUtil;

@Controller
@RequestMapping("conpub")
public class ConPubController {
	
	@Resource
	private ConPubService conPubService;
	
	@Resource
	private UserService userService;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@RequestMapping("list")
	public String list()
	throws Exception
	{
		map.put("contentTitle", null);
		map.put("contentCreateUserName", null);
		return "conpub/list";
	}
	
	/**
	 * 内容发布：分页
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/findContentForConPub", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findContentForConPub(Integer page,Integer rows){
		map.put("page", page);
		map.put("pageSize", rows);
		List<TContent> listdatas = conPubService.findContentForPublic(map) ;
		JSONArray jarr = new JSONArray();
		for(TContent content : listdatas){
			try {
				jarr.put(BeanUtils.describe(content));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		Integer total = conPubService.getTotalForConPub(map);
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	


	/**
	 * 增加搜索条件，返回结果集
	 * @param contentTitle
	 * @param contentCreateUserName
	 * @return
	 */
	@RequestMapping(value = "/addQueryForConPub", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addQueryForConPub(String contentTitle, String contentCreateUserName){
		map.put("contentTitle", contentTitle);
		map.put("contentCreateUserName", contentCreateUserName);
		int page =1;int rows =10;
		return findContentForConPub(page,rows);
	}
	
	/**
	 * 发布内容
	 * @param contentIds
	 * @return
	 */
	@RequestMapping(value = "/pubContent", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String pubContent(Integer contentId,Integer siteId,Integer channelId,Integer columnId){
		JSONObject json = new JSONObject();
		TContentPublish contentpub = new TContentPublish();
		if(contentId == null){
			json.put("success", false);
			json.put("message", "请选择发布内容！");
			return json.toString();
		}
		contentpub.setContentId(contentId);
		if(siteId == null){
			json.put("success", false);
			json.put("message", "请选择站点！");
			return json.toString();
		}
		contentpub.setSiteId(siteId);
		if(channelId == null){
			json.put("success", false);
			json.put("message", "请选择频道！");
			return json.toString();
		}
		contentpub.setChannelId(channelId);
		if(columnId == null){
			json.put("success", false);
			json.put("message", "请选择栏目！");
			return json.toString();
		}
		//判断该内容是否已发布到指定的站点、频道、栏目下
		if(conPubService.getconpub(contentId, siteId, channelId, columnId).size() != 0){
			json.put("success", false);
			json.put("message", "该内容已发布！");
			return json.toString();
		}
		contentpub.setColumnId(columnId);
		contentpub.setContentPubTime(DateUtil.getStringByDate(new Date(), "yyyy-MM-dd"));
		contentpub.setContentPubUserName(userService.getLoginUser().getUsername());
		//获取栏目的保存路径：
		TColumn column = conPubService.findById(columnId, TColumn.class);
		String columnpath = column.getColumnFilePath();
		//在栏目路径下创建年月日目录
		Calendar cal=Calendar.getInstance();
		String savecontentpubpath = PathUtil.buildFilePathByDate(columnpath, cal);
		//将html文件复制到指定的目录下：
		TContent content = conPubService.findById(contentId, TContent.class);
		String contentPath = content.getContentFilePath();//目标文件的路径
		String contentPubPath = FileUtil.copyFile(contentPath, savecontentpubpath);
		contentpub.setContentPubPath(contentPubPath);
		//设置文件的访问路径
		String serviceUrl = PathUtil.getServerUrl();
		contentpub.setContentPubUrl(serviceUrl+File.separator+contentPubPath.substring(PathUtil.getServicePath().length()+1));
		//设置文章标题
		contentpub.setContentTitle(content.getContentTitle());
		//确定发布
		Integer contentPubId = conPubService.saveContentPub(contentpub);
		if(contentPubId >0){
			json.put("success", true);
			json.put("message", "内容发布成功！");
			return json.toString();
		}else{
			json.put("success", false);
			json.put("message", "内容发布失败！");
			return json.toString();
		}
	}

	/**
	 * 设置内容的定时发布
	 * @param contentId
	 * @param siteId
	 * @param channelId
	 * @param columnId
	 * @param planDate
	 * @param planTime
	 * @return
	 */
	@RequestMapping(value = "/savePubplanTime", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String savePubplanTime(Integer contentId,Integer siteId,Integer channelId,Integer columnId,String planDate,String planTime){
		JSONObject json = new JSONObject();
		TContentPublish contentpub = new TContentPublish();
		contentpub.setContentId(contentId);
		contentpub.setSiteId(siteId);
		contentpub.setChannelId(channelId);
		contentpub.setColumnId(columnId);
		String date_str = planDate +" "+planTime;
		contentpub.setContentPubPlanTime(date_str);
		contentpub.setContentPubUserName(userService.getLoginUser().getUsername());
		String serviceUrl = PathUtil.getServerUrl();
		contentpub.setContentPubUrl(serviceUrl);
		contentpub.setContentPubPath(PathUtil.getServicePath());
		Integer contentpubId = conPubService.saveContentPub(contentpub);
		if(contentpubId >0){
			json.put("success", true);
			json.put("message", "定时发布设置成功！");
			return json.toString();
		}else{
			json.put("success", false);
			json.put("message", "定时发布设置失败！");
			return json.toString();
		}
			
	}
}



