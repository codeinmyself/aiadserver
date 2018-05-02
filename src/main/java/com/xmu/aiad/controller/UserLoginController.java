package com.xmu.aiad.controller;

import com.xmu.aiad.model.User;
import com.xmu.aiad.service.IUserLoginService;
import com.xmu.aiad.service.IUserService;
import com.xmu.aiad.util.BaseController;
import com.xmu.aiad.util.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class UserLoginController extends BaseController{
    private Logger logger=Logger.getLogger(this.getClass());

    public static String publicKey;
    private static String privateKey;

    @Resource
    private IUserService userService;

    @Resource
    private IUserLoginService userLoginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(HttpServletRequest request, HttpServletResponse response) {

        long telephone=Long.parseLong(request.getParameter("telephone"));
        String password=request.getParameter("password");
        System.out.println("password:"+password);
        User user=userLoginService.login(telephone,password);
        if(user!=null){
            return renderJsonSucc(null);
        }else{
            return renderJsonError("验证失败!");
        }
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(@RequestBody User user) {

        boolean flag=userService.addUser(user.getTelephone(),user.getPassword());
        if(flag){
            return renderJsonSucc(null);
        }else{
            return renderJsonError("验证失败!");
        }
    }

}
