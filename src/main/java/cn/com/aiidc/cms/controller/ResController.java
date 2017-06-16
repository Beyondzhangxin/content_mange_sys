package cn.com.aiidc.cms.controller;

import cn.com.aiidc.cms.entity.TResources;
import cn.com.aiidc.cms.service.ResService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Zhangx on 2017/4/1 at 18:30.
 */
@Controller
@RequestMapping("resmgr")
public class ResController
{
    @Resource
    private ResService resService;


    public ResService getResService()
    {
        return resService;
    }

    public void setResService(ResService resService)
    {
        this.resService = resService;
    }

    @RequestMapping("list")
    public String list()
    {
        return "resmgr/list";
    }

    @RequestMapping(value = "save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String save(@ModelAttribute TResources tResources)
    {

        try
        {
            resService.resSave(tResources);
            return new JSONObject().put("msg", "保存成功！").toString();
        } catch (JSONException e)
        {
            e.printStackTrace();
            return new JSONObject().put("msg", "保存失败！").toString();

        }

    }

    @RequestMapping("listall")
    @ResponseBody
    public List<TResources> getall()
    {
        return resService.getall();
    }


    @RequestMapping(value = "pagelist", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getIndexList(int page, int rows)
    {
        List<TResources> list = resService.getPagelist(page, rows);
        JSONObject object = new JSONObject();
        object.put("total", resService.getSize());
        object.put("rows", new JSONArray(list));
        return object.toString();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public String deleteList(@RequestParam Integer[] ids)
    {

        try
        {
            for (Integer id : ids)
            {
                resService.delete(id);

            }
            return new JSONObject().put("msg", "删除成功！").toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject().put("msg", "删除失败！").toString();

        }
    }




}

