package com.demo.test;

import com.demo.common.ThriftServerLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ThriftServerLoader thriftServerLoader = new ThriftServerLoader();
        thriftServerLoader.load();
        System.out.println( "Hello World!" );
    }
}
