package vn.iotstar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple in-memory message broker WITHOUT heartbeat to avoid TaskScheduler issues
        config.enableSimpleBroker("/topic", "/queue");
        
        // Designate the "/app" prefix for messages bound for @MessageMapping-annotated methods
        config.setApplicationDestinationPrefixes("/app");
        
        // Enable user-specific destinations
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the "/ws" endpoint with custom handshake handler and interceptor
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new UserHandshakeHandler())
                .addInterceptors(new HttpHandshakeInterceptor())
                .withSockJS();
    }
    
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        // Increase message size limits
        registration.setMessageSizeLimit(128 * 1024); // 128 KB
        registration.setSendBufferSizeLimit(512 * 1024); // 512 KB
        registration.setSendTimeLimit(20000); // 20 seconds
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
    }
    
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
    }
}

