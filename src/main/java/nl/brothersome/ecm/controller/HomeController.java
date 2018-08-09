package nl.brothersome.ecm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

	@Autowired
	Environment env;

	@GetMapping("/")
	public String index() {
		return "Welcome to home page";
	}

	@PostMapping("/")
	public String handleRequest (@RequestBody String str) {

		return "Input not recognized";
	}
}
