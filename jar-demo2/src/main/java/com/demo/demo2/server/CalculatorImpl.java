package com.demo.demo2.server;

import com.demo.demo2.thrift.Calculator;
import org.apache.thrift.TException;

public class CalculatorImpl implements Calculator.Iface {

	@Override
	public int add(int num1, int num2) throws TException {
		return num1+ num2;
	}

}
