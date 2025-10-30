package vn.iotstar.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            
            if (session != null) {
                // Lấy user ID từ session
                Object userIdObj = session.getAttribute("userId");
                if (userIdObj != null) {
                    String userId = userIdObj.toString();
                    System.out.println("🔐 WebSocket handshake for user ID: " + userId);
                    return new StompPrincipal(userId);
                }
            }
        }
        
        // Fallback to default behavior
        return super.determineUser(request, wsHandler, attributes);
    }
    
    // Custom Principal class to hold user ID
    private static class StompPrincipal implements Principal {
        private final String name;
        
        public StompPrincipal(String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return name;
        }
    }
}
