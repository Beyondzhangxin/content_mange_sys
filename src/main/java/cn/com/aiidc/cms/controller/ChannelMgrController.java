package cn.com.aiidc.cms.controller;

import java.lang.reflect.InvocationTargetException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.aiidc.cms.entity.TChannel;
import cn.com.aiidc.cms.entity.TSite;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.ChannelMgrService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.DateUtil;

@Controller
@RequestMapping("/chmgr")
public class ChannelMgrController {
	@Resource
	private ChannelMgrService channelMgrService;
	
	@Resource
	private UserService userService;
	
	public ChannelMgrService getChannelMgrService() {
		return channelMgrService;
	}

	public void setChannelMgrService(ChannelMgrService channelMgrService) {
		this.channelMgrService = channelMgrService;
	}

	private Map<String,Object> map = new HashMap<String, Object>();
	
	@RequestMapping(value="list")
	public String list() throws Exception{
		map.put("channelName", null);
		map.put("siteId", null);
		map.put("channelAppr", 2);
		return "chmgr/list";
	}
	
	/**
	 * 添加频道
	 * @param channelName
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value="addChannel",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addChannel(@RequestParam("channelName") String channelName,@RequestParam("siteId") Integer siteId)
	{
		JSONObject json = new JSONObject();
		TChannel channel = new TChannel();
		//判断是否选择了站点
		if(siteId == null ){
			json.put("success", false);
			json.put("message", "请选择站点！");
			return json.toString();
		}
		channel.setSiteId(siteId);
		if(channelName == ""){
			json.put("success", false);
			json.put("message", "频道名称不能为空！");
			return json.toString();
		}
		//判断频道名是否已被占用
		Integer channelId = -1;
		List<TChannel> channels = channelMgrService.checkChannelByChanelName(siteId, channelName, channelId);	
		if(channels.size() !=0){
			json.put("success", false);
			json.put("message", "同一个站点下，该频道已存在！");
			return json.toString();
		}
		channel.setChannelName(channelName);
		channel.setIsDisplay(1);
		channel.setCreateUserId(userService.getLoginUser().getUserId());
		channel.setCreateTime(DateUtil.getStringByDate( new Date(), "yyyy-MM-dd"));
		channel.setChannelAppr(0);//0 未审核  1 审核通过   2 审核未通过
		//保存站点
		channelId =channelMgrService.addChannel(channel);
		if(channelId == -1){
			json.put("success", false);
			json.put("message", "添加频道失败");
			return json.toString();
		}
		json.put("success", true);
		json.put("message", "添加频道成功");
		return json.toString();
	}

	/**
	 * 编辑频道
	 * @param channelId
	 * @param channelName
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value="/updateChannel",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String updateChannel(@RequestParam("channelId") Integer channelId,
			@RequestParam("channelName") String channelName,
			@RequestParam("siteId") Integer siteId)
	{
		JSONObject json = new JSONObject();
		TChannel channel = channelMgrService.findByChannelId(channelId);
		if(siteId == null ){
			json.put("success", false);
			json.put("message", "请选择站点");
			return json.toString();
		}
		//判断该频道能否被编辑
		if(channel.getChannelAppr() == 1){
			json.put("success", false);
			json.put("message", "审核通过后的频道，不能被编辑！");
			return json.toString();
		}
		channel.setSiteId(siteId);
		if(channelId==null){
			json.put("success", false);
			json.put("message", "操作失败！");
			return json.toString();
		}
		channel.setChannelId(channelId);
		if(channelName == ""){
			json.put("success", false);
			json.put("message", "频道名称不能为空！");
			return json.toString();
		}
		List<TChannel> list = channelMgrService.checkChannelByChanelName(siteId, channelName, channelId);
		if(list.size() != 0){
			json.put("success", false);
			json.put("message", "同一站点下，该频道以存在！");
			return json.toString();
		}
		channel.setChannelName(channelName);
		channel.setIsDisplay(1);
		channel.setCreateUserId(userService.getLoginUser().getUserId());
		channel.setCreateTime(DateUtil.getStringByDate( new Date(), "yyyy-MM-dd"));
		channel.setChannelAppr(0);//0 未审核  1 审核通过   2 审核未通过
		channelMgrService.UpdateChannel(channel);
		json.put("success", true);
		json.put("message", "编辑频道成功");
		return json.toString();
	}
	
	
	/**
	 * 删除频道
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value="/deleteByChannelId",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteByChannelId(Integer channelId){
		TChannel channel = channelMgrService.findByChannelId(channelId);
		JSONObject json = new JSONObject();
		if(channel.getChannelAppr() == 1){//审核通过
			channel.setIsDisplay(0);
			channelMgrService.updateIsDisplayForChannel(channel);
			json.put("success", true);
			json.put("message", "伪删除成功！");
			return json.toString();
		}
		channelMgrService.deleteChannel(channel);
		json.put("success", true);
		json.put("message", "删除频道完成");
		return json.toString();
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/listdataBypage",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listdataBypage(Integer page , Integer rows){
		map.put("page", page);map.put("pageSize", rows);
		//获取分页数据
		List<TChannel> channels = channelMgrService.findAllChannelBypage(map);
		JSONArray jarr = new JSONArray();
		for(TChannel channel : channels){
			Map<String, Object> channelMap;
			try {
				channelMap = BeanUtils.describe(channel);
				TSite site = channelMgrService.findClassById(channel.getSiteId(), TSite.class);
				channelMap.put("siteName", site.getSiteName());
				TUser user = channelMgrService.findClassById(channel.getCreateUserId(), TUser.class);
				channelMap.put("createUserName", user.getUserRealname());
				if(channel.getAppUserId() != null){
					user = channelMgrService.findClassById(channel.getAppUserId(),TUser.class);
					channelMap.put("apprUserName", user.getUserRealname());
				}else{
					channelMap.put("apprUserName", "");
				}
				jarr.put(channelMap);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		//获取满足条件的记录数
		int total = channelMgrService.gettotalForPage(map);
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	
	/**
	 * 根据审核的三个状态进行过滤：
	 * 0 未审核  1 审核通过   2 审核未通过
	 * @param channelAppr
	 * @return
	 */
	@RequestMapping(value="/checkdataByChannelAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String checkdataByChannelAppr(Integer channelAppr){
		map.put("channelName", null);
		map.put("siteId", null);
		map.put("channelAppr", channelAppr);
		Integer page =1;
		Integer rows =10;
		return listdataBypage(page ,rows);
	}
	
	/**
	 * 添加搜索条件
	 * @param channelNamee
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addQueryCriteria",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addQueryCriteria(String channelName,Integer siteId)
	{
		map.put("channelName", channelName);
		map.put("siteId", siteId);
		Integer page =1;
		Integer rows =10;
		return listdataBypage(page ,rows);
	}
	
	/**
	 * 瑞
	 * 根据频道Id查询频道信息
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value="/findByChannelId",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findByChannelId(Integer channelId){
		TChannel channel = channelMgrService.findByChannelId(channelId);
		JSONObject json = new JSONObject(channel);
		return json.toString();
	}
	
	/**
	 * 获取通过某站点下，通过审核的频道
	 * 注：当频道id为null时，返回所有通过审核的频道
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value="/getChannelByApprPass",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getChannelByApprPass(Integer siteId){
		List<TChannel> channels  = channelMgrService.getChannelByApprPass(siteId);
		JSONArray jarr = new JSONArray();
		for(TChannel channel : channels){
			try {
				jarr.put(BeanUtils.describeToJson(channel));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return jarr.toString();
	}
}
