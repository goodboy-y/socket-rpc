package com.cupid.service.handle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.cupid.core.RpcRequest;
import com.cupid.core.RpcResponse;

public class ServerHandle implements Runnable {

	private Map<String, Object> node = new HashMap<>();

	private Socket socket;

	public ServerHandle(Map<String, Object> node, Socket socket) {
		super();
		this.node = node;
		this.socket = socket;
	}

	@Override
	public void run() {

		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		try {
			input = new ObjectInputStream(socket.getInputStream());
			RpcRequest request = (RpcRequest) input.readObject();
			Class<?>[] parameterTypes = request.getParameterTypes();
			Object[] arguments = request.getParameters();

			output = new ObjectOutputStream(socket.getOutputStream());

			Object service = node.get(request.getClassName());
			Method method = service.getClass().getMethod(request.getMethodName(), parameterTypes);
			Object result = method.invoke(service, arguments);

			RpcResponse response = new RpcResponse();
			response.setResult(result);
			response.setRequestId(request.getRequestId());
			response.setError(null);
			output.writeObject(response);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
