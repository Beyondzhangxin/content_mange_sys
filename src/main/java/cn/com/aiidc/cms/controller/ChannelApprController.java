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
import cn.com.aiidc.cms.entity.TSite;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.ChannelApprService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.Chinese2PinYin;
import cn.com.aiidc.common.util.PathUtil;

@Controller
@RequestMapping("chrappr")
public class ChannelApprController {

	@Resource
	private ChannelApprService channelApprService;
	
	public ChannelApprService getChannelApprService() {
		return channelApprService;
	}

	public void setChannelApprService(ChannelApprService channelApprService) {
		this.channelApprService = channelApprService;
	}
	
	private Map<String, Object> map = new HashMap<String,Object>();
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="list")
	public String list() {
		map.put("channelName", null);
		map.put("siteId",null);
		return "chappr/list";
	}
	
	/**
	 * 频道审核分页管理
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getDataByChannelAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getDataByChannelAppr(int page ,int rows){
		map.put("page", page);
		map.put("pageSize", rows);
		JSONArray jarr = new JSONArray();
		List<TChannel> channels = channelApprService.getDataForChannelAppr(map);
		for(TChannel channel :channels){
			try {
				Map<String,Object >  apprMap = BeanUtils.describe(channel);
				apprMap.put("siteName",  channelApprService.findById(channel.getSiteId(), TSite.class).getSiteName());
				apprMap.put("username", channelApprService.findById(channel.getCreateUserId(),TUser.class ).getUserRealname());
				jarr.put(apprMap);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		Integer total = channelApprService.getTotalForAppr(map);
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	
	/**
	 * 添加搜索条件
	 * @param siteId  站点id
	 * @param channelName 频道名称
	 * @return
	 */
	@RequestMapping(value="addSearchCriteria",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addSearchCriteria(Integer siteId,String channelName){
		map.put("channelName", channelName);
		map.put("siteId",siteId);
		int page=1 ;int rows=10;
		return getDataByChannelAppr(page ,rows);
	}
	
	/**
	 * 频道的审核
	 * @param channelId
	 * @param channelAppr
	 * @param noPassReason
	 * @return
	 */
	@RequestMapping(value="channelAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String channelAppr(Integer channelId,Integer channelAppr,String noPassReason){
		System.out.println("==================开始审核=========================");
		TChannel channel = channelApprService.findById(channelId, TChannel.class);
		channel.setChannelAppr(channelAppr);
		//获取审核人的信息
		channel.setAppUserId(userService.getLoginUser().getUserId());
		channel.setNoPassReason(noPassReason);
		if(channelAppr == 1){//审核通过:创建文件夹
			String channelFileName = Chinese2PinYin.getPinYinHeadChar(channel.getChannelName())+channel.getChannelId();
			channel.setChannelFileName(channelFileName);
			System.out.println("文件夹名："+channelFileName);//--------------------------------------------------------
			TSite site = channelApprService.findById(channel.getSiteId(), TSite.class);
			String channelFilePath = PathUtil.getFilePathForDirectory(channelFileName, site.getSitePath());
			channel.setChannelFilePath(channelFilePath);
			System.out.println("文件夹的保存路径："+channelFilePath);//--------------------------------------------------------
		}
		channelApprService.updateChannel(channel);
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("message", "保存频道审核结果成功！");
		return json.toString();
	}
	
}
