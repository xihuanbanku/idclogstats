package com.isinonet.ismartnet.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * 读取property 文件 工具类
 * Created by Administrator on 2018-06-07.
 * 2018-06-07
 */
public class PropUtils {

    public static Properties loadProps(String file) {
        Properties props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties temp = new Properties();
        for(Map.Entry entry : props.entrySet()) {
            temp.setProperty(entry.getKey().toString().replace("jdbc.", ""), entry.getValue().toString());
        }
        temp.setProperty("user", props.getProperty("jdbc.username"));
        return temp;
    }
}
