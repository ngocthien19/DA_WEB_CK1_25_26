package vn.iotstar.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.service.UserDetailsImpl;

import java.util.Map;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                    WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            
            // Get authentication from Security Context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication != null && authentication.isAuthenticated() 
                && !"anonymousUser".equals(authentication.getPrincipal())) {
                
                if (authentication.getPrincipal() instanceof UserDetailsImpl) {
                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                    Integer userId = userDetails.getId();
                    
                    // Store user ID in session for WebSocket
                    if (session != null) {
                        session.setAttribute("userId", userId);
                        System.out.println("🔑 Stored user ID in session for WebSocket: " + userId);
                    }
                    
                    // Also store in WebSocket attributes
                    attributes.put("userId", userId);
                }
            }
        }
        
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                WebSocketHandler wsHandler, Exception exception) {
        // Nothing to do after handshake
    }
}
