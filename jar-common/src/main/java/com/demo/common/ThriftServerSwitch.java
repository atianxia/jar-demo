package com.demo.common;

import org.apache.thrift.transport.TTransportException;

/**
 * Created by luoyong on 16-9-14.
 */
public interface ThriftServerSwitch {

    void startup() throws TTransportException;

    void shutdown();
}
