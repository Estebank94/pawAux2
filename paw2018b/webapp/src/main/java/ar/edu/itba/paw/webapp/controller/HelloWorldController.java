package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.UserService;

@Controller
public class HelloWorldController {
	
	 @Autowired
	 @Qualifier("userServiceImpl")
	 private UserService us;
	
	@RequestMapping("/")
	public ModelAndView helloWorld() {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("greeting", "PAW");
		return mav;
	}

	@RequestMapping("/processForm")
	public ModelAndView processForm(@RequestParam("doctorName") String name, @RequestParam("specialty") String specialty) {
		final ModelAndView mav = new ModelAndView("specialists");
		mav.addObject("name", name);
		mav.addObject("specialty", specialty);
		return mav;
	}
}
