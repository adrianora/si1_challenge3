package lab3.spring.serpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardView {

	@RequestMapping("/")
	public String dashboardView() {
		return "index";
	}

}
