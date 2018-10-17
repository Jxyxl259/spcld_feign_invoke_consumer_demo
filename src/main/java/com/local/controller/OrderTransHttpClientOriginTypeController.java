package com.local.controller;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName OrderTransHttpClientOriginTypeController
 * @Description 原始的 HttpClient 调用方式
 * @Author jiangxy
 * @Date 2018\10\16 0016 14:01
 * @Version 1.0.0
 */
@Controller
public class OrderTransHttpClientOriginTypeController {



    @RequestMapping(value = "/httpget_2",method = RequestMethod.GET)
    @ResponseBody
    public String hello_2() {
        String jsonString = "{\"orderNo\":10000,\n" +
                "\t\"orderContent\":\"传参订单\"\n" +
                "}";
        try {
            String url = "http://localhost:8001/order/add?token=JXY0225";
            String responseBody = send(url, jsonString);
            System.out.println(responseBody);
            jsonString = responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }


    public static String send(String url, /*Map<String,String> map, String encoding*/ final String content) throws Exception{
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        if(map!=null){
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//        }
        //设置参数到请求对象中
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        httpPost.setEntity(new StringEntity(content, ContentType.create("application/json","UTF-8")));

        //System.out.println("请求地址："+url);
        //System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        //httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();

        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if(!(200 == statusCode)){
            System.out.println("invoke failed : status code : " + statusCode);
            if (entity != null) {
                body = EntityUtils.toString(entity);
                System.out.println("responseBody" + body);
                EntityUtils.consume(entity);
            }

            //释放链接
            response.close();
            return null;
        }


        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }
        //释放链接
        response.close();
        return body;
    }

}
