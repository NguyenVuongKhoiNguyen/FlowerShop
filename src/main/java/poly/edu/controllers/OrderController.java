package poly.edu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {

	@RequestMapping(value = { "/dashboard", "/dashboard/order" })
	public String doGet(Model model) {
		model.addAttribute("currentPage", "order");
		return "order-dashboard";
	}
}
