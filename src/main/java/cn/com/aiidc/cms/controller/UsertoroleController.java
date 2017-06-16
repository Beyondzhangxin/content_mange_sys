package cn.com.aiidc.cms.controller;

import cn.com.aiidc.cms.entity.TRole;
import cn.com.aiidc.cms.entity.TUserRole;
import cn.com.aiidc.cms.service.UserRoleService;
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
 * Created by Zhangx on 2017/4/21 at 15:58.
 */
@RequestMapping("usertorole")
@Controller
public class UsertoroleController
{
    @Resource
    UserRoleService userRoleService;

    public UserRoleService getUserRoleService()
    {
        return userRoleService;
    }

    public void setUserRoleService(UserRoleService userRoleService)
    {
        this.userRoleService = userRoleService;
    }

    @RequestMapping("list")
    public String list()
    {
        return "usertorole/list";
    }

    @RequestMapping("getrolelistbyuserid")
    @ResponseBody
    public List<TRole> getRolelistbyuserid(@RequestParam int id)
    {
        return userRoleService.getRolelistbyUserId(id);
    }

    @RequestMapping("getunselectedrolesbyuserid")
    @ResponseBody
    public List<TRole> getUnselectedRolesbyUserid(@RequestParam int id)
    {
        return userRoleService.getUnselectedRolesByUserId(id);
    }

    @RequestMapping(value = "save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String usertorolesave(HttpServletRequest ssr){
        JSONObject jsonObject=new JSONObject(ssr.getParameter("rolelist"));
        int length= jsonObject.getInt("total");
        int userId= Integer.parseInt(ssr.getParameter("userId"));
        JSONArray jsonArray=jsonObject.getJSONArray("rows");
        List<TUserRole> userroleList=userRoleService.getUserRolelistByUserId(userId);

        try
        {
            userRoleService.deleteAll(userroleList);
            for (int i = 0; i < length; i++)
            {
                int roleId=jsonArray.getJSONObject(i).getInt("roleId");
                userRoleService.save(userId,roleId);
            }
            return "修改成功！";
        } catch (JSONException e)
        {
            e.printStackTrace();
            return "修改失败！";
        }

    }

}
