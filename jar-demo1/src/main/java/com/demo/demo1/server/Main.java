package com.demo.demo1.server;

import com.demo.common.ThriftServerLoader;

/**
 * Created by luoyong on 16-9-14.
 */
public class Main {
    public static void main(String[] args) {
        ThriftServerLoader loader = new ThriftServerLoader();
        loader.load();
    }
}
