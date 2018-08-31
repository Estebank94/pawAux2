package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
		mav.addObject("search", new Search());
		return mav;
	}

	@RequestMapping("/processForm")
	public ModelAndView processForm(@ModelAttribute("search") Search theSearch) {
		final ModelAndView mav = new ModelAndView("specialists");
		return mav;
	}
}
