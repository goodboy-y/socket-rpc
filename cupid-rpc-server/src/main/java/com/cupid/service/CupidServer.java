package com.cupid.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.cupid.service.handle.ServerHandle;

public class CupidServer {

	private static Map<String, Object> node = new HashMap<>();

	public static void publish(final Object service) throws IOException {
		if (service == null)
			throw new IllegalArgumentException("service instance == null");
		String serviceName = service.getClass().getAnnotation(ServiceName.class).value();
		node.put(serviceName, service);
		System.out.println("publish service " + serviceName);
	}

	public static void run(int port) throws IOException {
		if (port <= 0 || port > 65535) {
			throw new IllegalArgumentException("Invalid port " + port);
		}
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(port);
		while (true) {
			try {
				Socket socket = server.accept();
				new Thread(new ServerHandle(node, socket)).start();
			} catch (Exception e) {

			}
		}
	}
}
