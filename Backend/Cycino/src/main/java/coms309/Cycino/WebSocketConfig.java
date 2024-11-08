package coms309.Cycino;
<<<<<<< HEAD
=======

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
>>>>>>> 33-blackjack-game-view
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
<<<<<<< HEAD
//@ConditionalOnProperty(value = "websockets.enabled", havingValue = "true", matchIfMissing = true)

=======
@ConditionalOnProperty(value = "websockets.enabled", havingValue = "true", matchIfMissing = true)
>>>>>>> 33-blackjack-game-view
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}