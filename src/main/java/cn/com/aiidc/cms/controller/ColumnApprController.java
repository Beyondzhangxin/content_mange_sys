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
import cn.com.aiidc.cms.entity.TSite;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.ColumnApprService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.Chinese2PinYin;
import cn.com.aiidc.common.util.PathUtil;

@Controller
@RequestMapping("columnappr")
public class ColumnApprController {

	@Resource
	private ColumnApprService columnApprService;

	public ColumnApprService getColumnApprService() {
		return columnApprService;
	}

	public void setColumnApprService(ColumnApprService columnApprService) {
		this.columnApprService = columnApprService;
	}
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@Resource
	private UserService userService;
	
	@RequestMapping("list")
	public String list(){
		map.put("siteId", null);
		map.put("channelId", null);
		map.put("columnName", null);
		return "columnAppr/list";
	}
	

	/**
	 * 获取栏目未审核的分页数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="pageForColumnAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String pageForColumnAppr(Integer page,Integer rows){
		map.put("page", page);
		map.put("pageSize", rows);
		List<TColumn> columns = columnApprService.getPageDataForColumnAppr(map);
		JSONArray jarr = new JSONArray();
		for(TColumn column : columns){
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> columnMap = BeanUtils.describe(column);
				columnMap.put("siteName", columnApprService.findById(column.getSiteId(), TSite.class).getSiteName());
				columnMap.put("channelName", columnApprService.findById(column.getChannelId(), TChannel.class).getChannelName());
				columnMap.put("userName", columnApprService.findById(column.getColumnCreateUserId(),TUser.class).getUserRealname());
				Integer pid = column.getParentId();
				if(pid !=0){
					columnMap.put("parentName", columnApprService.findById(pid, TColumn.class).getColumnName());
				}else{
					columnMap.put("parentName","无");
				}
				jarr.put(columnMap);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		Integer total = columnApprService.getTotalForColumnAppr(map);
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	

	/**
	 * 添加搜索条件，返回结果集
	 * @param siteId  站点id
	 * @param channelId  频道id
	 * @param columnName  栏目名称
	 * @return
	 */
	@RequestMapping(value="addSearchCriteriaForColumnAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addSearchCriteriaForColumnAppr(Integer siteId,Integer channelId,String columnName){
		map.put("siteId", siteId);
		map.put("channelId", channelId);
		map.put("columnName", columnName);
		int page=1 ;int rows=10;
		return pageForColumnAppr(page,rows);
	}
	
	@RequestMapping(value="columnAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String columnAppr(Integer columnId,Integer columnAppr,String noPassReason){
		System.out.println("==================开始栏目审核=========================");
		TColumn column = columnApprService.findById(columnId, TColumn.class);
		column.setColumnAppr(columnAppr);
		//获取审核人的信息
		column.setColumnApprUserId(userService.getLoginUser().getUserId());
		column.setNoPassReason(noPassReason);
		if(columnAppr == 1){//审核通过:创建文件夹
			String columnFileName = Chinese2PinYin.getPinYinHeadChar(column.getColumnName())+column.getColumnId();
			column.setColumnFileName(columnFileName);
			System.out.println("文件夹名："+columnFileName);//--------------------------------------------------------
			TChannel channel = columnApprService.findById(column.getChannelId(), TChannel.class);
			String columnFilePath = PathUtil.getFilePathForDirectory(columnFileName,channel.getChannelFilePath());
			column.setColumnFilePath(columnFilePath);
			System.out.println("文件夹的保存路径："+columnFilePath);//--------------------------------------------------------
		}
		columnApprService.updateColumn(column);
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("message", "保存栏目审核结果成功！");
		return json.toString();
	}
	
}
