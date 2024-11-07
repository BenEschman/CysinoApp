package coms309.Cycino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@ComponentScan(basePackages = "coms309.Cycino")
=======
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.websocket"})
>>>>>>> 33-blackjack-game-view
public class CycinoApplication {
	public static void main(String[] args) {
		SpringApplication.run(CycinoApplication.class, args);
	}

}
