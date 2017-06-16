package cn.com.aiidc.cms.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.aiidc.cms.entity.TTemplate;
import cn.com.aiidc.cms.entity.TTemplateClass;
import cn.com.aiidc.cms.service.TemplateMgrService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.FileUtil;
import cn.com.aiidc.common.util.PathUtil;

@Controller
@RequestMapping("modmgr")
public class ModMgrController {
	@Resource
	private TemplateMgrService templateMgrService;
	
	private Map<String,Object> map = new HashMap<String,Object>();
	
	@RequestMapping("list")
	public String list()throws Exception
	{
		map.put("templateName", null);
		return "model/list";
	}
	
	
	/**
	 * 模板分页
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="listdataByPage",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listdataByPage(Integer page,Integer rows) 
	{
		
		map.put("page", page);
		map.put("pageSize", rows);
		List<TTemplate> templates = templateMgrService.gettemplatesBypage(map);
		JSONArray jarr = new JSONArray();
		for(TTemplate template : templates){
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> modMap = BeanUtils.describe(template);
				Integer templateClass =template.getTemplateClass();
				modMap.put("templateClassName", templateMgrService.findById(templateClass, TTemplateClass.class).getTempaleAbstract());
				jarr.put(modMap);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			
		}
		JSONObject json = new JSONObject();
		int total = templateMgrService.getCountForTemplate(map);
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	

	/**
	 * 添加搜索添加，返回结果集
	 * @param templateName
	 * @return
	 */
	@RequestMapping(value="/addQueryCondition",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addQueryCondition(String templateName) 
	{
		map.put("templateName", templateName);
		Integer page =1 ;
		Integer rows =10;
		return listdataByPage(page, rows);
	}
	

	/**
	 * 查询所有的模板类别
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="findModClass",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findModClass() throws Exception
	{
		List<TTemplateClass> list = templateMgrService.findTemplateClass();
		JSONArray jarr = new JSONArray();
		for(TTemplateClass tempClass : list){
			jarr.put(BeanUtils.describe(tempClass));
		}
		return jarr.toString();
	}
	
	
	/**
	 * 添加模板
	 * @param templateName
	 * @param templateClass
	 * @param multipartFile
	 * @return
	 */
	@RequestMapping(value="/addTemplate",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addTemplate(@RequestParam("templateName")String templateName,
			@RequestParam("templateClass") Integer templateClass,
			@RequestParam("modfile") MultipartFile multipartFile)
	{
		TTemplate template = new TTemplate();
		JSONObject json = new JSONObject();
		if(templateName == null){
			json.put("success", false);
			json.put("message", "模板名称不能为空!");
			return json.toString();
		}
		//根据模板名称，校验模板是否已存在
		Integer templateId =-1;
		List<TTemplate> list = templateMgrService.checkTemplate(templateName,templateId);
		if(list.size() !=0){
			json.put("success", false);
			json.put("message", "该模板已存在！");
			return json.toString();
		}
		template.setTemplateName(templateName);
		if(templateClass == null){
			json.put("success", false);
			json.put("message", "请选择模板的类型!");
			return json.toString();
		}
		template.setTemplateClass(templateClass);
		//模板保存文件夹名：
		String saveFileName="modupload";
		String savePath = PathUtil.getFilePathForDirectory(saveFileName, PathUtil.getServicePath());
		//获取上传文件的文件名
		String templateFileName= multipartFile.getOriginalFilename();
		template.setTemplateFileName(templateFileName);
		//判断上传文件的后缀名是否是html
		String SuffixName = templateFileName.substring(templateFileName.lastIndexOf("."));
		if(!".html".equals(SuffixName)){
			json.put("success", false);
			json.put("message", "请上传html文件！");
			return json.toString();
		}
		try {
			String templateFilePath =savePath+File.separator+templateFileName;
			multipartFile.transferTo(new File(templateFilePath));
			template.setTemplateFilePath(templateFilePath);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//创建模板的访问路径
		String ServerUrl = PathUtil.getServerUrl();
		String templateFileUrl = ServerUrl +File.separator+saveFileName+File.separator+templateFileName;
		template.setTemplateFileUrl(templateFileUrl);
		//保存上传模板的信息
		templateId = templateMgrService.saveTemplate(template);
		if(templateId == -1){
			json.put("success", false);
			json.put("message", "添加模板失败！");
			return json.toString();
		}else{
			json.put("success", true);
			json.put("message", "添加模板成功！");
			return json.toString();
		}
	}
	
	/**
	 * 删除模板
	 * @param templateId
	 * @return
	 */
	@RequestMapping(value="/deleteTemplate",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteTemplate(Integer templateId){
		TTemplate template = templateMgrService.findById(templateId, TTemplate.class);
		//删除指定路径下的文件
		String templateFilePath = template.getTemplateFilePath();
		boolean ok = FileUtil.deleteFile(templateFilePath);
		JSONObject json = new JSONObject();
		if(ok){
			templateMgrService.deleteModById(template);
			json.put("message", "删除模板成功！");
			return json.toString();
		}else{
			json.put("message", "删除模板失败！");
			return json.toString();
		}
	}
	
	/**
	 * 根据模板类别，查询模板
	 * @param templateClass
	 * @return
	 */
	@RequestMapping(value="/findModByModclass",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findModByModclass(Integer templateClass)
	{
		List<TTemplate> templates = templateMgrService.findModByModClass(templateClass);
		JSONArray jarr = new JSONArray();
		for(TTemplate template :templates){
			try {
				jarr.put(BeanUtils.describe(template));
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