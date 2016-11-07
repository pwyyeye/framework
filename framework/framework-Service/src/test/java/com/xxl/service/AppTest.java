package com.xxl.service;

import java.net.URL;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import common.utils.SpringUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void test123()
    {
    	String url = new String("http://localhost:8888/frameworkWeb/loginController/home");
		String uri = "/loginController/home/a.jpg";
		String uri2 = "/loginController.jsp";
		String uri3 = "/loginController/home.do";
		String uri4 = "/loginController/home.do?123=http://localhost:8888/frameworkWeb/loginController/home";
		String page = getReqPage(uri);
		String page2 = getReqPage(uri2);
		String page3 = getReqPage(uri3);
		String page4 = getReqPage(uri4);
		System.out.println(page);
		System.out.println(page2);
		System.out.println(page3);
		System.out.println(page4);
		
		
//		String page = getFileSuffix(uri);
//		String page2 = getFileSuffix(uri2);
//		String page3 = getFileSuffix(uri3);
//		String page4 = getFileSuffix(uri4);
//		System.out.println(page);
//		System.out.println(page2);
//		System.out.println(page3);
//		System.out.println(page4);

    }
    
    
    private String getReqPage(String filename) {
		if (filename == null)
			return null;
		int i = filename.lastIndexOf("/");
		int j = filename.indexOf("?");
		if (i < 0 || i > filename.length() - 1) {
			return "";
		}else if(i == (filename.length() - 1)){
			//情况二
			String temp="";
			temp= filename.substring(0, i-1);
			filename=filename.substring(temp.lastIndexOf("/")+1, i);
			return filename;
		}
		
		/**
			例如：filename =  情况1、"/frameworkWeb/loginController/home";
							情况2、/frameworkWeb/loginController/
							情况3、/frameworkWeb/loginController/login.do
			
		*/
		
		int k=i==0?-1:filename.substring(0, i-1).lastIndexOf("/");
		if(k==-1){
			return j < i ? filename.substring(i + 1) : filename.substring(i + 1, j);
		}else{
			return filename.substring(k+1, i);
		}
		
	}
    
    
    private String getFileSuffix(String filename) {
		if (filename == null)
			return null;
		if(filename.indexOf("?")!=-1){
			filename=filename.substring(0, filename.indexOf("?"));
		}
		int i = filename.lastIndexOf(".");
		if (i < 0 || i >= filename.length() - 1) {
			int j = filename.lastIndexOf("/");
			if(j>=0){
				return "do";
			}else {
				return "";
			}
			
		}
		return filename.substring(i + 1);
	}
}
