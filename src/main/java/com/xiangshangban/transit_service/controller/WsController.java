package com.xiangshangban.transit_service.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.transit_service.bean.RequestMessage;
import com.xiangshangban.transit_service.bean.ResponseMessage;

@RestController
@RequestMapping("/wsController")
public class WsController {
		@MessageMapping("/welcome")
	    @SendTo("/topic/getResponse")
	    public ResponseMessage say(RequestMessage message) {
	        System.out.println(message.getName());
	        return new ResponseMessage("welcome," + message.getName() + " !");
	    }
}
