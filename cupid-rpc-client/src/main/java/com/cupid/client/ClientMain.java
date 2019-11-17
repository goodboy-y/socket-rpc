package com.cupid.client;

import com.cupid.api.ExtendService;
import com.cupid.api.HelloService;

/**
 * Client Main Class
 *
 */
public class ClientMain {
	public static void main(String[] args) {
		HelloService helloService = CupidClient.refer(HelloService.class, "127.0.0.1", 8888);
		String sayHello = helloService.sayHello("lingling");
		System.out.println(sayHello);

		System.out.println("调用加法运算");
		ExtendService extendService = CupidClient.refer(ExtendService.class, "127.0.0.1", 8888);
		Integer result = extendService.add(3, 2);
		System.out.println("3 + 2 = " + result);
	}
}
