package cn.com.aiidc.cms.controller;

import cn.com.aiidc.cms.entity.TAuthorities;
import cn.com.aiidc.cms.service.AuthService;
import org.json.JSONArray;
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
 * Created by 57332 on 2017/3/28.
 */

@Controller
@RequestMapping("authmgr")
public class AuthMgrController
{
    @Resource
    private AuthService authService;

    public AuthService getAuthService()
    {
        return authService;
    }

    public void setAuthService(AuthService authService)
    {
        this.authService = authService;
    }


    @RequestMapping("list")
    public String list()
    {
        return "authmgr/list";
    }

    @RequestMapping("getall")
    @ResponseBody
    public List<TAuthorities> getall(){
        return authService.getList();
    }

    /**
     * url:authmgr/listall.do
     *
     * @param page
     * @param rows
     * @return {total: rows:}   rows是authority的数组字符串
     */
    @RequestMapping(value = "listall", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getList(int page, int rows)

    {
        JSONObject jo = new JSONObject();

        List<TAuthorities> list = authService.getIndexList(page, rows);

        jo.put("total", authService.getListSize());
        jo.put("rows", new JSONArray(list));
        return jo.toString();
    }

    /*
    *保存权限实体类，返回msg， 若保存成功，返回“保存成功”，若保存失败，返回“保存失败”*/

    /**
     * url: authmgr/authSave.do
     *
     * @param authorities
     * @return if success:{mssg:"保存成功"}  if fail: {mssg:"保存失败"}
     */
    @RequestMapping(value = "authSave", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String saveAuth(@ModelAttribute TAuthorities authorities)
    {
        try
        {
            authService.saveAuthority(authorities);
            String result = new JSONObject().put("mssg", " 保存成功").toString();
            return result;
//            return "{\"mssg\":\"success\"}";
        } catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject().put("mssg", "保存失败").toString();
//            return  "{\"mssg\":\"success\"}";
        }

    }

    /**
     * url:authmgr/deleteAuthlist
     *
     * @param authorityIds authorityId的int数组
     * @return if success:{mssg:"保存成功"}  if fail: {mssg:"保存失败"}
     */
    @RequestMapping(value = "deleteAuthlist", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String deleteAuthList(@RequestParam Integer[] authorityIds)
    {
        try
        {
            for (int i : authorityIds)
            {
                TAuthorities tAuthorities = authService.getAuthById(i);
                authService.deleteAuth(tAuthorities);
            }
            return "{\"mssg\":\"删除成功！\"}";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "{\"mssg\":\"删除失败！\"}";
        }
    }


}
