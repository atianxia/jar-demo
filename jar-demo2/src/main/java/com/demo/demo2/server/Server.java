package com.demo.demo2.server;

import com.demo.common.ThriftServerSwitch;
import com.demo.demo2.thrift.Calculator;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class Server implements ThriftServerSwitch {
	private static final int SERVERPORT = 9095;
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
