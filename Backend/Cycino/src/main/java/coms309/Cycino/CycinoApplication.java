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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
//@ComponentScan(basePackages = {"com.example.demo.websocket"})
=======
>>>>>>> origin/main
=======
>>>>>>> 3ceb9f608fe498d31b25b105b0d36f93e0c15cfd
=======
>>>>>>> 3ceb9f608fe498d31b25b105b0d36f93e0c15cfd
=======
>>>>>>> 47a8e53f595138108cedba48c49442e76e6cd5ae
=======
>>>>>>> 47a8e53f595138108cedba48c49442e76e6cd5ae
=======
>>>>>>> 3c1ad4b9af669384c9c557ef14089f9628fb63e4
=======
>>>>>>> fb22897a593f79f28db15feb863178a21fc992f6
@ComponentScan(basePackages = "coms309.Cycino")
@ComponentScan(basePackages = "coms309.Cycino.WebSocketConfig")
public class CycinoApplication {
	public static void main(String[] args) {
		SpringApplication.run(CycinoApplication.class, args);
	}

}
