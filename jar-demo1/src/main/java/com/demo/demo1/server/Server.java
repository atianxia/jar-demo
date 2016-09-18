package com.demo.demo1.server;

import com.demo.common.ThriftServerSwitch;
import com.demo.demo1.thrift.Calculator;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class Server implements ThriftServerSwitch {
	private static final int SERVERPORT = 9094;
	private static final int TIMEOUT = 60000;
	TServer server;

	@Override
	public void startup() throws TTransportException {
		Calculator.Processor<CalculatorImpl> processor = new Calculator.Processor<CalculatorImpl>(new CalculatorImpl());
		TServerTransport serverTransport = new TServerSocket(SERVERPORT, TIMEOUT);

		server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
		server.serve();

	}

	@Override
	public void shutdown() {
		server.stop();
	}
}
