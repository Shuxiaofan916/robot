package com.robot.demo;

import com.robot.demo.utils.JsonUtil;
import groovy.util.logging.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.Map;


@Slf4j
@Component
public class WebSocketConfig {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    public WebSocketClient webSocketClient() {
        try {
            WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://127.0.0.1:5701"),new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    log.info("[websocket] 连接成功");
                }


                @Override
                public void onMessage(String message) {
                    //log.info("[websocket] 收到消息={}",message);
                    try {
                        Map<String,Object> map = JsonUtil.string2Obj(message);
                        if (map.containsKey(map.get("post_type")) && "message".equals(map.get("post_type"))){
                            System.out.println(map.get("user_id")+":"+map.get("message"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("[websocket] 退出连接");
                }

                @Override
                public void onError(Exception ex) {
                    log.info("[websocket] 连接错误={}",ex.getMessage());
                }
            };
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}