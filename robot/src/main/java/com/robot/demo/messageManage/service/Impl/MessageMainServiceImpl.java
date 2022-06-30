package com.robot.demo.messageManage.service.Impl;

import cn.hutool.http.HttpRequest;
import com.robot.demo.messageManage.service.MessageMainService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageMainServiceImpl implements MessageMainService {
    @Override
    public void onMessage(Map<String, Object> map) throws Exception {
        //String body = HttpRequest.post("123").body("!23").timeout(20000).execute().body();

    }
}
