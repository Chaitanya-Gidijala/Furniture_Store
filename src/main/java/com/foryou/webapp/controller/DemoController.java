package com.foryou.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DemoController {

	// add a request mapping for /leaders

	
	@GetMapping("/leaders")
	public String showLeaders() {

		return "leaders";
	}

	// add request mapping for /systems

	@GetMapping("/systems")
	public String showSystems() {

		return "systems";
	}
	
	@GetMapping("/navigation")
	public String nav() {
		return "navigation";
	}

	
	@GetMapping("/register")
	public String signUp() {
		return "sign-up";
	}
	
//	@GetMapping("/loginSuccess")
//	public void getLoginInfo(
//	        @AuthenticationPrincipal UserDetails authentication,
//	        HttpServletResponse response) throws IOException {
//	    if (authentication.getAuthorities()
//	            .contains(new SimpleGrantedAuthority("ADMIN"))) {
//	        response.sendRedirect("/navigation");
//	    } else {
//	        response.sendRedirect("/navigation");
//	    }
//	}
}
