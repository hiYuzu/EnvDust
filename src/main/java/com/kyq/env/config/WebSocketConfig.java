package com.kyq.env.config;

import javax.annotation.Resource;

import com.kyq.env.Handler.WebsocketHandler;
import com.kyq.env.Interceptor.WebSocketIntercepor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket拦截器
 *
 * @author kyq
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    /**
     * 注册Websocket
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        /* 1.注册兼容websocket的配置 */
        String websocketUrl = "/websocket";
        registry.addHandler(this.websocketHandler, websocketUrl).
                addInterceptors(this.webSocketIntercepor).
                setAllowedOrigins("*");

        /* 2.注册socketJS的配置 */
        String socketUrl = "/socketJS";
        registry.addHandler(this.websocketHandler, socketUrl).
                addInterceptors(this.webSocketIntercepor).
                withSockJS();
    }

    @Resource
    private WebsocketHandler websocketHandler;

    @Resource
    private WebSocketIntercepor webSocketIntercepor;
//	
//	@Bean
//	public WebsocketHandler websocketHandler(){
//		return new WebsocketHandler();
//	}
//	
//	public WebSocketIntercepor webSocketIntercepor(){
//		return new WebSocketIntercepor();
//	}

}
