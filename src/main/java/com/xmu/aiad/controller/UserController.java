package com.xmu.aiad.controller;


import com.xmu.aiad.model.User;
import com.xmu.aiad.service.IUserService;
import com.xmu.aiad.util.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/")
public class UserController extends BaseController{


    @Resource(name="userService")
    private IUserService userService;

    @RequestMapping(value = "/query/{id}",method = RequestMethod.GET)
    @ResponseBody
    public User getUserByNickname(@PathVariable int id){

        return userService.getUserById(id);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String  hh(){
        return "hello";
    }

}
