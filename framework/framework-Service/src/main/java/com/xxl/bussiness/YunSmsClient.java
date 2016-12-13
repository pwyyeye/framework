package com.xxl.bussiness;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Mlink下行请求java示例 <br>
 * <Ul>
 * <Li>本示例定义几种下行请求消息的使用方法</Li>
 * <Li>本示例依赖于 commons-codec，commons-httpclient，commons-logging等几个jar包</Li>
 * </Ul>
 */
public class YunSmsClient {

    private String mtUrl="http://http.yunsms.cn/tx/";
    String uid = "188608";		//数字用户名
    String pwd = "670b14728ad9902aecba32e22fa4f6bd";		//MD5 32位 密码
    /**
     * 启动测试
     * @param args
     */
    public static void main(String[] args) {
        YunSmsClient test = new YunSmsClient();
        try {
//			test.sendSMS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * 发送短信
     * @param mobile 发送号码，多个用","号隔开
     * @param content 短信内容
     */
    
    public boolean sendSMS(String mobile,String content) throws Exception {
//        String mobile = "18695722800";	//发送号码，多个用","号隔开
		//发送的内容
//        String content = "尊敬的用户您好！您的验证码为：160302，请勿将验证码泄露于他人【西西里】";//new String("您的验证码为：160302【西西里】".getBytes(ecodeform));
    	//组成url字符串
        StringBuilder smsUrl = new StringBuilder();
        smsUrl.append(mtUrl);
        smsUrl.append("?uid=" + uid);
        smsUrl.append("&pwd=" + pwd);
        smsUrl.append("&mobile=" + mobile);
        smsUrl.append("&encode=utf8");
        smsUrl.append("&content=" +URLEncoder.encode(content));
        
        //发送http请求，并接收http响应
        String resStr = doGetRequest(smsUrl.toString());
//        System.out.println(resStr);
        if (resStr.equals("100"))
		{
			return true; //成功
		}
		else
		{
			return false;//失败
		}

    }
    /**
     * 发送http GET请求，并返回http响应字符串
     * 
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doGetRequest(String urlstr) {
        String res = null;
        HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
        client.getParams().setIntParameter("http.socket.timeout", 10000);
        client.getParams().setIntParameter("http.connection.timeout", 5000);

        HttpMethod httpmethod = new GetMethod(urlstr);
        try {
            int statusCode = client.executeMethod(httpmethod);
            if (statusCode == HttpStatus.SC_OK) {
                res = httpmethod.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpmethod.releaseConnection();
        }
        return res;
    }

    /**
     * 发送http POST请求，并返回http响应字符串
     * 
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doPostRequest(String urlstr) {
        String res = null;
        HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
        client.getParams().setIntParameter("http.socket.timeout", 10000);
        client.getParams().setIntParameter("http.connection.timeout", 5000);
        
        HttpMethod httpmethod =  new PostMethod(urlstr);
        try {
            int statusCode = client.executeMethod(httpmethod);
            if (statusCode == HttpStatus.SC_OK) {
                res = httpmethod.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpmethod.releaseConnection();
        }
        return res;
    }

}
