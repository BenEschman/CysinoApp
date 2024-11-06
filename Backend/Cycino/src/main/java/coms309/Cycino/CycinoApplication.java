package coms309.Cycino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"coms"})
public class CycinoApplication {


	public static void main(String[] args) {
		SpringApplication.run(CycinoApplication.class, args);
	}

}
