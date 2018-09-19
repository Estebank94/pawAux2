package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.UserService;

import java.util.*;

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
		Optional<CompressedSearch> compressedSearch =  doctorService.findDoctors(theSearch);
		List<Doctor> doctorsList = null;
		if(compressedSearch.isPresent()) {
			doctorsList = compressedSearch.get().getDoctors();
		}
		else {
			compressedSearch = doctorService.listDoctors();
			mav.addObject("notFound", "no");
			if(compressedSearch.isPresent()) {
				doctorsList = compressedSearch.get().getDoctors();
			}
			theSearch.setName("");
			theSearch.setInsurance("no");
			theSearch.setSpecialty("");
		}

		mav.addObject("doctorList", doctorsList);
		mav.addObject("insuranceList", searchService.listInsurances().get());
		mav.addObject("sexList", compressedSearch.get().getSex());
		mav.addObject("insuranceNameList", compressedSearch.get().getInsurance());
		mav.addObject("previousSearch", theSearch);

		return mav;
	}

	@RequestMapping("/specialist/{doctorId}")
    public ModelAndView doctorDescription(@PathVariable Integer doctorId, @ModelAttribute("search") Search search){

	    final ModelAndView mav = new ModelAndView("specialist");

	    Doctor doctor = doctorService.findDoctorById(doctorId).get();

		mav.addObject("doctor", doctor);
		mav.addObject("insuranceNameList", doctor.getInsurance().keySet());
        mav.addObject("insuranceList", searchService.listInsurances().get());


	    return mav;
    }
}
