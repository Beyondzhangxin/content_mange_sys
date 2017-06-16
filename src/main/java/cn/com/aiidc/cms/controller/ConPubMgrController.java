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

import cn.com.aiidc.cms.entity.TChannel;
import cn.com.aiidc.cms.entity.TColumn;
import cn.com.aiidc.cms.entity.TContentPublish;
import cn.com.aiidc.cms.entity.TSite;
import cn.com.aiidc.cms.service.ConPubMgrService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.FileUtil;

@Controller
@RequestMapping("conhaspub")
public class ConPubMgrController {
	
	@Resource
	private ConPubMgrService conPubMgrService;

	public ConPubMgrService getConPubMgrService() {
		return conPubMgrService;
	}

	public void setConPubMgrService(ConPubMgrService conPubMgrService) {
		this.conPubMgrService = conPubMgrService;
	}
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@RequestMapping("list")
	public String list() throws Exception {
		map.put("siteId", null);
		map.put("channelId", null);
		map.put("columnId", null);
		map.put("contentTitle", null);
		return "conhaspub/list";
	}
	
	
	/**
	 * 已发布内容的分页管理
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getConPubForPage",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getConPubForPage(int page , int rows){
		map.put("page", page);
		map.put("pageSize", rows);
		List<TContentPublish> conpubs = conPubMgrService.getContPubForPage(map);
		JSONArray jarr = new JSONArray();
		for(TContentPublish conpub : conpubs){
			Map<String, Object> conpubmap;
			try {
				conpubmap = BeanUtils.describe(conpub);
				conpubmap.put("siteName", conPubMgrService.findById(conpub.getSiteId(), TSite.class).getSiteName());
				conpubmap.put("channelName", conPubMgrService.findById(conpub.getChannelId(), TChannel.class).getChannelName());
				conpubmap.put("columnName",  conPubMgrService.findById(conpub.getColumnId(), TColumn.class).getColumnName());
				jarr.put(conpubmap);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		Integer total = conPubMgrService.getTotalByMap(map);
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	
	/**
	 * 添加搜索条件
	 * @param siteId 站点id
	 * @param channelId 频道id
	 * @param columnId 栏目id
	 * @param contentTitle 内容标题
	 * @return
	 */
	@RequestMapping(value="/addSearchCriteria",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addSearchCriteria(Integer siteId ,Integer channelId ,Integer columnId,String contentTitle){
		map.put("siteId", siteId);
		map.put("channelId", channelId);
		map.put("columnId", columnId);
		map.put("contentTitle", contentTitle);
		int page =1; int rows =10;
		return getConPubForPage(page, rows);
	}
	
	/**
	 * 根据id进行删除
	 * @param contentPubId
	 * @return
	 */
	@RequestMapping(value="/deteleContentPublish",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deteleContentPublish(Integer contentPubId){
		TContentPublish conpub = conPubMgrService.findById(contentPubId, TContentPublish.class);
		String conpubPath = conpub.getContentPubPath();
		boolean ok = FileUtil.deleteFile(conpubPath);
		JSONObject json = new JSONObject();
		if(ok){
			conPubMgrService.deteleContentPublish(conpub);
			json.put("success", true);
			json.put("message","成功删除！");
			return json.toString();
		}else{
			json.put("success", false);
			json.put("message","删除失败！");
			return json.toString();
		}
	}
	
}
