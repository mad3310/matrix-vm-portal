package com.letv.mms.controller;

import org.springframework.ui.ModelMap;


/**
 * base controller
 * 
 * @author liunk
 */
public class BaseContorller {

    /**
     * 定向到错误页面
     * 
     * @return view
     */
    public String toErrorPage() {
        return "/error";
    }

    /**
     * 定向到错误页面
     * 
     * @param msg 错误消息
     * @param model ModelMap
     * @return view
     */
    public String toErrorPage(String msg, ModelMap model) {
        model.addAttribute("ERROR_MSG", msg);
        return this.toErrorPage();
    }

    /**
     * 定向到登录页面
     * 
     * @return view
     */
    public String toLoginPage() {
        return "redirect:/user/login.html";
    }

}
