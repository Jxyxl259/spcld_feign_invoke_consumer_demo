package com.local.controller;

import com.alibaba.fastjson.JSON;
import com.local.entity.Order;
import com.local.service.OrderClientServiceQuick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @ClassName OrderTestController
 * @Description
 * @Author jiangxy
 * @Date 2018\9\18 0018 14:57
 * @Version 1.0.0
 */
@Controller
public class OrderTestFeignController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderTestFeignController.class);

    private static final String POST_KEY = "jsondata";

    /**
     * 通过Feign来调用，抽取出Order项目中被调用的接口（注意访问路径是否带上下文）
     * 此处的接口仅供调用测试使用，实际情况是在代码中实现调用
     * 地址栏后加一个token参数，测试路径传参
     */
    @Autowired
    private OrderClientServiceQuick orderClientService;


/** 这个方法是调用 order-DEV项目（模拟改造） */
//    @RequestMapping(value="/order-service-create")
//    @ResponseBody
//    public void orderLocalTest(HttpServletRequest request, HttpServletResponse response){
//        LOG.info("本地模拟服务消费端，调用 order 服务接口 " );
//
//        String token = request.getParameter("token");
//        LOG.info("token ->" + token);
//
//        /* 模拟代码中的报文 */
//        String requestBodyStr = "bodybodybodybodybody";
//        String responseMessage = null;
//        try {
//            responseMessage = orderClientService.handleRequestInternal(requestBodyStr);
//        } catch (Exception e) {
//            System.out.println("调用服务提供端发生异常");
//            e.printStackTrace();
//        }
//
//        try {
//            System.out.println("接收到服务提供端原始响应信息" + responseMessage);
//            responseMessage = URLDecoder.decode(responseMessage, StandardCharsets.UTF_8.name());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        LOG.info("本地模拟服务消费端，收到 order 服务接口返回结果 " + responseMessage);
//
//        byte[] bytes = "调用服务成功".getBytes(StandardCharsets.UTF_8);
//
//        try {
//            ServletOutputStream os = response.getOutputStream();
//            response.setContentType("text/html; charset=UTF-8");
//            response.setContentLength(bytes.length);
//            os.write(bytes);
//            os.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * feign 的方式调用，API 返回值类型为 String 类型，
     * 服务端返回值类型为 对象类型，（注意需要加 @ResponseBody注解， 或是在类上添加 @RestController 注解）
     * 底层相当于 ：服务端的 SpringMvc 将返回对象 通过 MappingJackson2HttpMessageConverter这个消息转换器 转换为 json字符串 并加上默认的请求头信息 application/json;charset=UTF-8
     * 传到消费端，消费端的 Feign 根据 API的返回值类型（String类型），在类 SynchronousMethodHandler.decode() 中由 ResponseEntityDecoder.decode() 中调用 SpringDecoder.decode()来解析 responseBody 中的信息
     * 解析所使用的消息转换器依旧是 MappingJackson2HttpMessageConverter
     * @param request
     * @param response
     */
    @RequestMapping(value="/order-service-create")
    @ResponseBody
    public void orderLocalTest(HttpServletRequest request, HttpServletResponse response){
        LOG.info("本地模拟服务消费端，调用 order 服务接口 " );

        Order o = new Order(1000, new Date(), "bodybodybodybody", false);

        /* 模拟代码中的报文,将Order对象序列化为json字符串 */
        String requestBodyStr = JSON.toJSONString(o);
        String responseStatus = "";
        //Order responseStatus = null;
        try {
            responseStatus = orderClientService.add(requestBodyStr);
        } catch (Exception e) {
            System.out.println("调用服务提供端发生异常");
            e.printStackTrace();
        }

        System.out.println("接收到服务提供端原始响应信息" + responseStatus);

        byte[] bytes = "调用服务成功".getBytes(StandardCharsets.UTF_8);

        try {
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("text/html; charset=UTF-8");
            response.setContentLength(bytes.length);
            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * feign的方式调用，API返回类型为对象类型
     * 服务端返回值类型为对象类型（注意需要加 @ResponseBody注解， 或是在类上添加 @RestController 注解）
     * 底层相当于 ：服务端的 SpringMvc 将返回对象 通过 Jackson2MapperMappingConverter这个消息转换器 转换为 json字符串 并加上默认的请求头信息 application/json;charset=UTF-8
     * 传到消费端，消费端的 Feign 根据 API的返回值类型（对象类型），在类 SynchronousMethodHandler.decode() 决定如何来解析 responseBody 中的信息
     * @param request
     * @param response
     */
    @RequestMapping(value="/order-service-detail")
    @ResponseBody
    public void orderLocalTest_getOrderInfo(HttpServletRequest request, HttpServletResponse response){
        LOG.info("本地模拟服务消费端，调用 order 服务接口 " );

        Order responseStatus = null;
        try {
            responseStatus = orderClientService.getOrderInfo(new Order(6666,new Date(), "传参订单", false));
        } catch (Exception e) {
            System.out.println("调用服务提供端发生异常");
            e.printStackTrace();
        }

        System.out.println("接收到服务提供端原始响应信息" + responseStatus);

        byte[] bytes = "调用服务成功".getBytes(StandardCharsets.UTF_8);

        try {
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("text/html; charset=UTF-8");
            response.setContentLength(bytes.length);
            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过 feign 的方式调用，API返回值类型为对象类型，
     * 服务端将返回对象以 String 的形式返回（将返回对象序列化，并加请求头 application/json ，需做特殊处理，不然默认的请求头为 text/plain）
     * 消费端是可以直接将这个对象接收到的
     * @param request
     * @param response
     */
    @RequestMapping(value="/test/String2Order")
    @ResponseBody
    public void orderLocalTest_String2Order(HttpServletRequest request, HttpServletResponse response){
        LOG.info("本地模拟服务消费端，调用 order 服务接口 " );

        Order responseStatus = null;
        try {
            responseStatus = orderClientService.testString2Order(new Order(6666,new Date(), "传参订单", false));
        } catch (Exception e) {
            System.out.println("调用服务提供端发生异常");
            e.printStackTrace();
        }

        System.out.println("接收到服务提供端原始响应信息" + responseStatus);

        byte[] bytes = "调用服务成功".getBytes(StandardCharsets.UTF_8);

        try {
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("text/html; charset=UTF-8");
            response.setContentLength(bytes.length);
            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 通过 feign 的方式调用，API返回值类型为对象类型，
     * 服务端将返回对象以流的形式写入到 response 中（将返回对象序列化，并加请求头 application/json ），
     * 消费端是可以直接将这个对象接收到的
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/test/osresponse")
    @ResponseBody
    public String testWriteResponseByOutputStream(HttpServletRequest request, HttpServletResponse response){
        LOG.info("本地模拟服务消费端，调用 order 服务接口 " );

        Order responseStatus = null;
        try {
            responseStatus = orderClientService.testOutputStreamResponse(new Order(6666,new Date(), "传参订单", false));
        } catch (Exception e) {
            System.out.println("调用服务提供端发生异常");
            e.printStackTrace();
        }

        System.out.println("接收到服务提供端原始响应信息" + responseStatus);

        String orderString = JSON.toJSONString(responseStatus);

        return orderString;
    }
}













