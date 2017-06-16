package cn.com.aiidc.cms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.aiidc.cms.entity.TChannel;
import cn.com.aiidc.cms.entity.TColumn;
import cn.com.aiidc.cms.entity.TSite;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.ColumnMgrService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.DateUtil;


@Controller
@RequestMapping("itemmgr")
public class ColumnMgrController {
	
	@Resource
	private ColumnMgrService columnMgrService; 
	
	
	public ColumnMgrService getColumnMgrService() {
		return columnMgrService;
	}


	public void setColumnMgrService(ColumnMgrService columnMgrService) {
		this.columnMgrService = columnMgrService;
	}
	
	@Resource
	private UserService userService;
	
	private Map<String,Object> map = new  HashMap<String,Object>();

	@RequestMapping(value="list")
	public String list() throws Exception{
		map.put("siteId", null);
		map.put("channelId", null);
		map.put("columnName", null);
		map.put("columnAppr", 2);
		return "column/list";
	}
	
	/**
	 * 获取频道下，审核通过的栏目
	 * 注意：如果频道id为null,则获取全部通过审核的栏目数据
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value="getColumnByApprPass",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getColumnByApprPass(Integer channelId){
		List<TColumn> columns = columnMgrService.getColumnByApprPass(channelId);
		JSONArray jarr = new JSONArray();
		for(TColumn column : columns){
			try {
				jarr.put(BeanUtils.describe(column));
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
	
	/**
	 * 添加栏目
	 * @param column
	 * @param res
	 */
	@RequestMapping(value="addColumn",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addColumn(@ModelAttribute TColumn column,HttpServletResponse res)
	{
		JSONObject json = new JSONObject();
		if(column.getSiteId() == null){
			json.put("success", false);
			json.put("message", "请选择站点!");
			return json.toString();
		}
		if(column.getChannelId() == null){
			json.put("success", false);
			json.put("message", "请选择频道!");
			return json.toString();
		}
		if(column.getParentId()==null){
			column.setParentId(0);//当没有选择上级栏目时，该栏目为顶级栏目
		}
		if(column.getTemplateId()== null){
			json.put("success", false);
			json.put("message", "请选择栏目模板!");
			return json.toString();
		}
		if(column.getColumnName() == null){				
			json.put("success", false);
			json.put("message", "栏目名称不能为NULL!");
			return json.toString();
		}
		//校验该栏目在指定的频道下是否存在
		List<TColumn> list = columnMgrService.checkColumnByColumnName(column.getColumnId(), 
				column.getChannelId(), column.getColumnName());
		if(list.size() != 0 ){
			json.put("success", false);
			json.put("message", "该栏目已存在!");
			return json.toString();
		}
		column.setIsDisplay(1);
		column.setColumnCreateTime(DateUtil.getStringByDate(new Date(), "yyyy-MM-dd"));
		column.setColumnCreateUserId(userService.getLoginUser().getUserId());
		column.setColumnAppr(0);// 0 未审核  1 审核通过  2  审核未通过
		Integer columnId = columnMgrService.save(column);
		if(columnId == null ){
			json.put("success", false);
			json.put("message", "添加栏目的操作失败!");
			return json.toString();
		}else{
			json.put("success", true);
			json.put("message", "添加栏目成功!");
			return json.toString();
		}
	}
	
	/**
	 * 编辑栏目
	 * @param column
	 * @param res
	 */
	@RequestMapping(value="editColumn",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String editColumn(@ModelAttribute TColumn column,HttpServletResponse res)
	{
		JSONObject json = new JSONObject();
		if(column.getColumnId() == null){
			json.put("success", false);
			json.put("message", "编辑栏目失败，请重新编辑!");
			return json.toString();
		}
		if(column.getSiteId() == null){
			json.put("success", false);
			json.put("message", "请选择站点!");
			return json.toString();
		}
		if(column.getChannelId() == null){
			json.put("success", false);
			json.put("message", "请选择频道!");
			return json.toString();
		}
		if(column.getParentId()==null){
			column.setParentId(0);//当没有选择上级栏目时，该栏目为顶级栏目
		}
		if(column.getTemplateId()== null){
			json.put("success", false);
			json.put("message", "请选择栏目模板!");
			return json.toString();
		}
		if(column.getColumnName() == null){
			json.put("success", false);
			json.put("message", "栏目名称不能为NULL!");
			return json.toString();
		}
		//校验该栏目在指定的频道下是否存在
		List<TColumn> list = columnMgrService.checkColumnByColumnName(column.getColumnId(), 
				column.getChannelId(), column.getColumnName());
		if(list.size() != 0 ){
			json.put("success", false);
			json.put("message", "该栏目已存在!");
			return json.toString();
		}
		column.setIsDisplay(1);
		column.setColumnCreateTime(DateUtil.getStringByDate(new Date(), "yyyy-MM-dd"));
		column.setColumnCreateUserId(userService.getLoginUser().getUserId());
		column.setColumnAppr(0);// 0 未审核  1 审核通过  2  审核未通过
		//更新栏目
		columnMgrService.update(column);
		json.put("success", true);
		json.put("message", "成功编辑栏目!");
		return json.toString();
	}
	
	/**
	 * 删除栏目:
	 * 	注意:当栏目已被审核通过，则进行伪删除，暂时保留一段时间
	 * @param columnId
	 * @return
	 */
	@RequestMapping(value="/deleteColumnByColumnId",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteColumnByColumnId(Integer columnId){
		TColumn column = columnMgrService.findByColumnId(columnId);
		JSONObject json = new JSONObject();
		//判断该栏目是否有子栏目
		if(columnMgrService.findChildColumnByPid(column.getColumnId()).size() != 0){
			json.put("success", false);
			json.put("message", "请先删除该栏目的子栏目");
			return json.toString();
		}else{
			if(column.getColumnAppr() ==1){
				column.setIsDisplay(0);//审核通过后的栏目进行伪删除
				columnMgrService.update(column);
				json.put("success", true);
				json.put("message", "伪删除成功");
				return json.toString();
			}else{
				columnMgrService.deleteColumn(column);
				json.put("success", true);
				json.put("message", "删除栏目成功");
				return json.toString();
			}
		}
	}
	
	/**
	 * 获取栏目分页管理数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="getColumnForColumnPage",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getColumnForColumnPage(int page ,int rows){
		map.put("page", page);
		map.put("pageSize", rows);
		List<TColumn> columns = columnMgrService.getPageDataForColumnMgr(map);
		JSONArray jarr = new JSONArray();
		for(TColumn column : columns){
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> columnMap = BeanUtils.describe(column);
				columnMap.put("siteName", columnMgrService.findById(column.getSiteId(), TSite.class).getSiteName());
				columnMap.put("channelName", columnMgrService.findById(column.getChannelId(), TChannel.class).getChannelName());
				columnMap.put("userName", columnMgrService.findById(column.getColumnCreateUserId(),TUser.class).getUserRealname());
				Integer pid = column.getParentId();
				if(pid !=0){
					columnMap.put("parentName", columnMgrService.findById(pid, TColumn.class).getColumnName());
				}else{
					columnMap.put("parentName","无");
				}
				if(column.getColumnApprUserId() != null){
					columnMap.put("apprUserName", columnMgrService.findById(column.getColumnApprUserId(),TUser.class).getUserRealname());
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
		Integer total = columnMgrService.getTotalForColumnMgr(map);
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	
	/**
	 * 获取栏目管理的栏目树
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getColumnTreeForJson",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getColumnTreeForJson(){
		List<TColumn> columns = columnMgrService.buildColumnTree(map);
		JSONArray jarr = new JSONArray();
		for(TColumn column :columns){
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> columnMap = BeanUtils.describe(column);
				columnMap.put("siteName", columnMgrService.findById(column.getSiteId(), TSite.class).getSiteName());
				columnMap.put("channelName", columnMgrService.findById(column.getChannelId(), TChannel.class).getChannelName());
				columnMap.put("userName", columnMgrService.findById(column.getColumnCreateUserId(),TUser.class).getUserRealname());
				if(column.getColumnApprUserId() != null){
					columnMap.put("apprUserName", columnMgrService.findById(column.getColumnApprUserId(),TUser.class).getUserRealname());
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
		return jarr.toString();
	}
	/**
	 * 根据查询条件，返回查询结果集。
	 * 注意：此处需要进行判断返回什么样的结构数据
	 * @param siteId  站点id
	 * @param channelId  频道id
	 * @param columnName  栏目名字
	 * @return
	 */
	@RequestMapping(value="addColumnSearchCriteria",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addColumnSearchCriteria(Integer siteId,Integer channelId,String columnName){
		map.put("siteId", siteId);
		map.put("channelId", channelId);
		map.put("columnName", columnName);
		Integer columnAppr = (Integer) map.get("columnAppr");
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");
		if(columnAppr == 1){//审核通过，返回树状结构
			return getColumnTreeForJson();
		}else{// 审核未通过  或 未审核  ，返回分页数据
			return getColumnForColumnPage(page ,pageSize);
		}
	}
	
	
	/**
	 * 根据栏目的审核状态查询，返回查询结果集
	 * 注意：此处需要进行判断返回什么样的结构数据
	 * @param columnAppr
	 * @return
	 */
	@RequestMapping(value="findColumnByColumnAppr",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findColumnByColumnAppr(Integer columnAppr){
		map.put("siteId", null);
		map.put("channelId", null);
		map.put("columnName", null);
		map.put("columnAppr", columnAppr);
		Integer page = 1;
		Integer pageSize = 10;
		if(columnAppr == 1){//审核通过，返回树状结构
			return getColumnTreeForJson();
		}else{// 审核未通过  或 未审核  ，返回分页数据
			return getColumnForColumnPage(page ,pageSize);
		}
	}
	
	/**
	 * 根据栏目id查询栏目信息
	 * @param columnId
	 * @return
	 */
	@RequestMapping(value="/findByColumnId",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody 
	public String findByColumnId(Integer columnId){ 
		TColumn column = columnMgrService.findByColumnId(columnId);
		JSONObject json = new JSONObject(column);
		return json.toString();
	}

}


