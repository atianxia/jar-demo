package com.demo.demo2.client;

import com.demo.demo2.thrift.Calculator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Client {

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 9091;
	public static final int TIMEOUT = 30000;

	/**
	 *
	 * @param userName
	 */
	public void startClient(String userName) {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			// TProtocol protocol = new TCompactProtocol(transport);
			// TProtocol protocol = new TJSONProtocol(transport);
			Calculator.Client client = new Calculator.Client(
					protocol);
			transport.open();
			int result = client.add(1, 2);
			System.out.println("Thrify client result =: " + result);
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (null != transport) {
				transport.close();
			}
		}
	}

	public static void main(String[] args) throws TTransportException {
		Client client = new Client();
		client.startClient("Michael");
		
	}
}
