package com.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luoyong on 16-9-14.
 */
public class ThriftServerLoader {
    private static Logger logger = LoggerFactory.getLogger(ThriftServerLoader.class);

    private static final String DEFAULT_HANDLER_MAPPINGS_LOCATION = "META-INF/mgthrift.properties";

    private Map<String, ThriftServerSwitch> thriftServerSwitchMap;

    public static void main(String[] args) {
        ThriftServerLoader loader = new ThriftServerLoader();
        loader.load();
    }

    public void load() {
        Map<String, ThriftServerSwitch> serverSwitchMap = getThriftServerSwitchMap();
        if(serverSwitchMap == null || serverSwitchMap.isEmpty()){
            return;
        }
        for (Map.Entry<String, ThriftServerSwitch> entry : serverSwitchMap.entrySet()) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        logger.info("starting {} service......", entry.getKey());
                        entry.getValue().startup();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                for (Map.Entry<String, ThriftServerSwitch> entry : serverSwitchMap.entrySet()) {
                    try {
                        logger.info("stop {} service......", entry.getKey());
                        entry.getValue().shutdown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private Map<String, ThriftServerSwitch> getThriftServerSwitchMap() {
        if (thriftServerSwitchMap == null) {
            synchronized (this) {
                if (thriftServerSwitchMap == null) {
                    Properties props = loadAllProperties();
                    Map<String, ThriftServerSwitch> map = new ConcurrentHashMap<String, ThriftServerSwitch>(props.size());
                    if (props != null) {
                        for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements(); ) {
                            String key = (String) en.nextElement();
                            String className = props.getProperty(key);
                            try {
                                Class<?> thriftServerSwitchClass = Class.forName(className);
                                if (!ThriftServerSwitch.class.isAssignableFrom(thriftServerSwitchClass)) {
                                    throw new RuntimeException("Class [" + className + "] for load [" + key +
                                            "] does not implement the [" + ThriftServerSwitch.class.getName() + "] interface");
                                }
                                map.put(key, (ThriftServerSwitch) thriftServerSwitchClass.newInstance());
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (InstantiationException | IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    thriftServerSwitchMap = Collections.unmodifiableMap(map);
                    return map;
                }
            }
        }
        return Collections.emptyMap();
    }

    private Properties loadAllProperties() {
        Enumeration<URL> urls = null;
        try {
            urls = this.getClass().getClassLoader().getResources(DEFAULT_HANDLER_MAPPINGS_LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            InputStream is = null;
            try {
                URLConnection con = url.openConnection();
                is = con.getInputStream();
                try {
                    props.load(is);
                } finally {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return props;
    }

}
