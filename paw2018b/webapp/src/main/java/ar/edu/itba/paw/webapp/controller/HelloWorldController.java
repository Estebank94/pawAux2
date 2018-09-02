package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class HelloWorldController {
	
	 @Autowired
	 @Qualifier("userServiceImpl")
	 private UserService us;

	 @Autowired
	 private DoctorService doctorService;

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/")
	public ModelAndView helloWorld() {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("search", new Search());

		mav.addObject("insuranceList", searchService.listInsurances().get());
		return mav;
	}

	@RequestMapping("/processForm")
	public ModelAndView processForm(@ModelAttribute("search") Search theSearch) {
		final ModelAndView mav = new ModelAndView("specialists");
		Optional<List<Doctor>> doctors =  doctorService.findDoctors(theSearch);
		List<Doctor> doctorsList = null;
		if(doctors.isPresent()) {
			doctorsList = doctors.get();
		}
		else {
			doctors = doctorService.listDoctors();
			if(doctors.isPresent()) {
				doctorsList = doctors.get();
			}
		}

		mav.addObject("doctorList", doctorsList);
		return mav;
	}
}
