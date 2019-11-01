package com.tcb.env.Interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import com.tcb.env.util.DefaultArgument;

/**
 * [功能描述]：WebSocket握手拦截器
 * @author kyq
 */
@Component
public class WebSocketIntercepor implements HandshakeInterceptor {
	/* 获取拦截器日志 */
	Logger logger = LoggerFactory.getLogger(WebSocketIntercepor.class);
	
	 /**
	  * 握手之后
	  * 
	  */
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse arg1,
			WebSocketHandler arg2, Exception arg3) {

	}
	
	/**
	 * 握手之前
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> map) throws Exception {
		if(request instanceof ServletServerHttpRequest){
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			if (session != null) {
	            map.put(DefaultArgument.LOGIN_USER, session.getId());
	        }
		}
		return true;
	}

}
