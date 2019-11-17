package com.cupid.service;

import java.io.IOException;

import com.cupid.api.ExtendService;
import com.cupid.api.HelloService;

/**
 * Service Main Class
 *
 */
public class ServiceMain {
	public static void main(String[] args) throws IOException {
		HelloService helloService = new HelloServiceImpl();
		ExtendService extendService = new ExtendServiceImpl();
		CupidServer.publish(helloService);
		CupidServer.publish(extendService);
		CupidServer.run(8888);
	}
}
