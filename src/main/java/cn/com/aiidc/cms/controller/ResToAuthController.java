package cn.com.aiidc.cms.controller;

import cn.com.aiidc.cms.entity.TAuthorities;
import cn.com.aiidc.cms.entity.TAuthoritiesResources;
import cn.com.aiidc.cms.entity.TResources;
import cn.com.aiidc.cms.service.ResToAuthService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Zhangx on 2017/4/6 at 11:08.
 */
@Controller
@RequestMapping("restoauthmgr")
public class ResToAuthController
{
    @Resource
    private ResToAuthService resToAuthService;

    public ResToAuthService getResToAuthService()
    {
        return resToAuthService;
    }

    public void setResToAuthService(ResToAuthService resToAuthService)
    {
        this.resToAuthService = resToAuthService;
    }

    @RequestMapping("list")
    public String list()
    {
        return "restoauthmgr/list";
    }

    @RequestMapping("getauthlistbyres")
    @ResponseBody
    public List<TAuthorities> getAuthlistByRes(@RequestParam int id)
    {
        return resToAuthService.getauthByRes(id);

    }

    @RequestMapping("getreslistbyauthid")
    @ResponseBody
    public List<TResources> getReslistByAuthId(@RequestParam int id)
    {
        return resToAuthService.getResByAuthId(id);
    }


    @RequestMapping("getUnselectedAuthByResId")
    @ResponseBody
    public List<TAuthorities> getUnselectedAuthlistByResId(@RequestParam int id)
    {
        return resToAuthService.getUnselectedAuthByResId(id);
    }

    @RequestMapping("getunselectedreslistbyauthid")
    @ResponseBody
    public List<TResources> getUnselectedReslistbyAuthId(@RequestParam int id)
    {
        return resToAuthService.getUnselectedResByAuthId(id);
    }

    @RequestMapping(value = "save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String restoauthsave(HttpServletRequest ssr)
    {
        JSONObject jsonObject = new JSONObject(ssr.getParameter("reslist"));
        int length = jsonObject.getInt("total");
        int authId = Integer.parseInt(ssr.getParameter("authId"));
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        List<TAuthoritiesResources> restoauthlist = resToAuthService.getRestoAuthlistByAuthId(authId);
        try
        {
            resToAuthService.deleteAll(restoauthlist);
            for (int i = 0; i < length; i++)
            {
                int resId = jsonArray.getJSONObject(i).getInt("resourceId");
                resToAuthService.save(resId, authId);
            }

            return "修改成功！";
        } catch (JSONException e)
        {
            e.printStackTrace();
            return "修改失败！";
        }


    }

}
