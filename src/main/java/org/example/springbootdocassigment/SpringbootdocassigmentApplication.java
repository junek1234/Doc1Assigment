package org.example.springbootdocassigment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
@SpringBootApplication
public class SpringbootdocassigmentApplication {

	@RequestMapping("/")
	public String index() {
		return "<body bgcolor=\"#63d5ff\">" +
				"<h1>VIATAB</h1> " +
				"<h2>Under construction...</h2>" +
				"</body>";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootdocassigmentApplication.class, args);
	}

}
