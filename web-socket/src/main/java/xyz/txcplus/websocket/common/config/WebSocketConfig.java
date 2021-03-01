package xyz.txcplus.websocket.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

/**
 * 配置WebSocket
 * Description:
 * date: 2020/12/15 17:05
 *
 * @author TXC
 */
@Configuration
// 注解开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理
        config.enableSimpleBroker("/topic");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        config.setApplicationDestinationPrefixes("/app");
    }


    // 注册STOMP协议的节点(endpoint),并映射指定的url
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*").withSockJS();
    }

    // 配置发送与接收的消息参数，可以指定消息字节大小，缓存大小，发送超时时间
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration var1) {
    }

    // 设置输入消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
    @Override
    public void configureClientInboundChannel(ChannelRegistration var1) {
    }

    // 设置输出消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
    @Override
    public void configureClientOutboundChannel(ChannelRegistration var1) {
    }

    // 添加自定义的消息转换器，spring 提供多种默认的消息转换器，返回false,不会添加消息转换器，返回true，会添加默认的消息转换器，当然也可以把自己写的消息转换器添加到转换链中
    @Override
    public boolean configureMessageConverters(List<MessageConverter> var1) {
        return true;
    }


    // 自定义控制器方法的参数类型，有兴趣可以百度google HandlerMethodArgumentResolver这个的用法
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> var1) {
    }

    // 自定义控制器方法返回值类型，有兴趣可以百度google HandlerMethodReturnValueHandler这个的用法
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> var1) {
    }

}
