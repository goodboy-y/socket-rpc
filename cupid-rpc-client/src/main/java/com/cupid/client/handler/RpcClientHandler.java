package com.cupid.client.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.UUID;

import com.cupid.core.RpcException;
import com.cupid.core.RpcRequest;
import com.cupid.core.RpcResponse;

public class RpcClientHandler<T> implements InvocationHandler {

	private String host;
	private int port;
	private Class<T> target;

	public RpcClientHandler(String host, int port, Class<T> target) {
		this.host = host;
		this.port = port;
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Socket socket = new Socket(host, port);
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream input = null;
		try {
			RpcRequest rpcRequest = new RpcRequest();
			rpcRequest.setRequestId(UUID.randomUUID().toString());
			rpcRequest.setMethodName(method.getName());
			rpcRequest.setParameters(args);
			rpcRequest.setParameterTypes(method.getParameterTypes());
			rpcRequest.setClassName(target.getSimpleName());

			output.writeObject(rpcRequest);
			output.flush();

			input = new ObjectInputStream(socket.getInputStream());
			RpcResponse response = (RpcResponse) input.readObject();
			if (!rpcRequest.getRequestId().equals(response.getRequestId())) {
				throw new RpcException("request id is not response id");
			}
			return response.getResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			input.close();
			output.close();
			socket.close();
		}
		return null;
	}
}
