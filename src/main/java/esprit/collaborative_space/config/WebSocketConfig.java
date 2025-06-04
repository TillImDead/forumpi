package esprit.collaborative_space.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final VideoCallHandler videoCallHandler;

    public WebSocketConfig(VideoCallHandler videoCallHandler) {
        this.videoCallHandler = videoCallHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(videoCallHandler, "/video-call").setAllowedOrigins("*");
    }
}
