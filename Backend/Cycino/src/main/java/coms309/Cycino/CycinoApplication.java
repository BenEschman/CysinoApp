package coms309.Cycino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
<<<<<<< HEAD
//@ComponentScan(basePackages = {"com.example.demo.websocket"})
=======
>>>>>>> origin/main
@ComponentScan(basePackages = "coms309.Cycino")
@ComponentScan(basePackages = "coms309.Cycino.WebSocketConfig")
public class CycinoApplication {
	public static void main(String[] args) {
		SpringApplication.run(CycinoApplication.class, args);
	}

}
