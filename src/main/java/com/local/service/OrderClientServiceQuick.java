package com.local.service;

import com.local.entity.Order;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@FeignClient(value="microservice-order-provider")
public interface OrderClientServiceQuick {


    @RequestMapping(value = "/order/add", method = RequestMethod.POST, consumes={"application/json"}/*, produces={"application/json"}*/)
    public String add(@RequestBody String order);


    @RequestMapping(value = "/order/detail", method=RequestMethod.POST, consumes={"application/json;charset=utf-8"}/*,produces={"application/json;charset=utf-8"}*/)
    public Order getOrderInfo(@RequestBody Order order);


    @RequestMapping(value = "/test/String2Order", method=RequestMethod.POST)
    public Order testString2Order(@RequestBody Order order);

    @RequestMapping(value = "/test/osresponse", method=RequestMethod.POST)
    public Order testOutputStreamResponse(@RequestBody Order order);
}
