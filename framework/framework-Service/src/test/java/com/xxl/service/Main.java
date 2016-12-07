package com.xxl.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
			System.out.println(123);
			context.start();
		} catch (Exception e) {
//			log.error("== DubboProvider context start error:",e);
			System.out.println("== DubboProvider context start error:"+e);
		}
		synchronized (Main.class) {
			while (true) {
				try {
					Main.class.wait();
				} catch (InterruptedException e) {
//					log.error("== synchronized error:",e);
					System.out.println("== synchronized error:"+e);
				}
			}
		}
	}

}
