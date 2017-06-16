package cn.com.aiidc.cms.controller;

import cn.com.aiidc.cms.entity.TAuthorities;
import cn.com.aiidc.cms.entity.TRolesAuthorities;
import cn.com.aiidc.cms.service.RoleToAuthService;
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
 * Created by Zhangx on 2017/4/6 at 21:54.
 */
@RequestMapping("roletoauthmgr")
@Controller
public class RolesToAuthController
{
    @Resource
    private RoleToAuthService roleToAuthService;

    public RoleToAuthService getRoleToAuthService()
    {
        return roleToAuthService;
    }

    public void setRoleToAuthService(RoleToAuthService roleToAuthService)
    {
        this.roleToAuthService = roleToAuthService;
    }

    @RequestMapping("list")
    public String list()
    {
        return "roletoauthmgr/list";
    }

     @RequestMapping("authlistbyroleid")
     @ResponseBody
    public List<TAuthorities> getAuthlistByRoleId(@RequestParam int id)
    {
        return roleToAuthService.getAuthlistByRoleId(id);
    }

    @RequestMapping("unselectedauthlist")
    @ResponseBody
    public List<TAuthorities> getUnselectedAuthlistByRoleId(@RequestParam int id){
        return roleToAuthService.getUnselectedAuthByRoleId(id);
    }


    @RequestMapping(value = "save",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String restoauthsave(HttpServletRequest ssr)
    {
        JSONObject jsonObject = new JSONObject(ssr.getParameter("authlist"));
        int length = jsonObject.getInt("total");
        int roleId = Integer.parseInt(ssr.getParameter("roleId"));
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        List<TRolesAuthorities> roletoauthlist = roleToAuthService.getrestoauthlistByRoleid(roleId);
        try
        {
            roleToAuthService.deleteAll(roletoauthlist);
            for (int i = 0; i < length; i++)
            {
                int authId = jsonArray.getJSONObject(i).getInt("authorityId");
                roleToAuthService.save(roleId, authId);
            }

            return "修改成功！";
        } catch (JSONException e)
        {
            e.printStackTrace();
            return "修改失败！";
        }


    }

}
