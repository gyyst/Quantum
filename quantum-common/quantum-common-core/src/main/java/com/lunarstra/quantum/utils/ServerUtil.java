package com.lunarstra.quantum.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/1/26 11:05
 */

@Component
@Lazy
public class ServerUtil {
    private static final String HOST_ADDRESS;

    static {
        HOST_ADDRESS = ServerUtil.getV4OrV6IP();
    }

    @Value("${server.port}")
    private int serverPort;

    /**
     * <h2>获取本机的外网ip地址</h2>
     *
     * @return
     */
    public static String getV4OrV6IP() {
        String ip = null;
        // 访问其他网站获取ip
        // 测试网站 https://ipw.cn/
        String test = "http://test.ipw.cn";
        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(test);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            while ((read = in.readLine()) != null) {
                inputLine.append(read);
            }
            ip = inputLine.toString();

        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (ip == null) {
            // 没有获取到ip 给默认ip
            ip = "120.229.119.86";
        }
        return ip;
    }

    public String GetServerURL() {
        return HOST_ADDRESS + ":" + serverPort;
    }

}
