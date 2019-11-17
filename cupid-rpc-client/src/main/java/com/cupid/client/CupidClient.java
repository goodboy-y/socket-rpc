package com.cupid.client;

import java.lang.reflect.Proxy;

import com.cupid.client.handler.RpcClientHandler;

public class CupidClient {

	@SuppressWarnings("unchecked")
	public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) {
		if (interfaceClass == null)
			throw new IllegalArgumentException("Interface class == null");
		if (!interfaceClass.isInterface())
			throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
		if (host == null || host.length() == 0)
			throw new IllegalArgumentException("Host == null!");
		if (port <= 0 || port > 65535)
			throw new IllegalArgumentException("Invalid port " + port);
		T proxy = (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass },
				new RpcClientHandler<T>(host, port, interfaceClass));
		return proxy;
	}

}
