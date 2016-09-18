package com.demo.demo1.server;

import com.demo.demo1.thrift.Calculator;
import org.apache.thrift.TException;

public class CalculatorImpl implements Calculator.Iface {

	@Override
	public int add(int num1, int num2) throws TException {
		return num1+ num2;
	}

}
