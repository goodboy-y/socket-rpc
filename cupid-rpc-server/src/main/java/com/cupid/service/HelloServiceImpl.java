package com.cupid.service;

import com.cupid.api.HelloService;

@ServiceName("HelloService")
public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		String result = "Hello " + name;
		System.out.println(result);
		return result;
	}

}
