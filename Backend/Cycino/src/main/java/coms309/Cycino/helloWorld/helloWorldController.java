package coms309.Cycino.helloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class helloWorldController {
    @GetMapping("/helloworld")
    public String helloWorld() {return "Hello World";}
}
