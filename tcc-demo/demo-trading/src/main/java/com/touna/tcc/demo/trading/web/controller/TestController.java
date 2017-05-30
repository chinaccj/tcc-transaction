package com.touna.tcc.demo.trading.web.controller;

import com.touna.tcc.demo.trading.service.OrderService;
import com.touna.tcc.demo.trading.web.bean.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by chenchaojian on 16/7/29.
 */
@Controller
@RequestMapping("/")
public class TestController {
    @Resource
    private OrderService orderService;

    @RequestMapping(value="order", method = RequestMethod.GET)
    public @ResponseBody
    Response getShopInJSON() {
        try {

            orderService.placeOrder("1","1",11f);

            return new Response("hello world!!");

        }catch (Exception e){
            e.printStackTrace();
            return new Response("error!!");

        }


    }
}

