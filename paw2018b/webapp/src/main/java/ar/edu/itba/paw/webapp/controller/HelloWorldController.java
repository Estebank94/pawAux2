package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.web.servlet.NoHandlerFoundException;

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
		mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());
		mav.addObject("specialtyList", searchService.listSpecialtiesWithDoctors().get());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if(name != "anonymousUser"){
			/*TODO: TOASK: Si chequeamos con el taglib de spring security es necesario un double check?*/
			//ver como resulto esto ..........

			mav.addObject("loggedInName", name);
			System.out.println(name);
			System.out.println("hay alguien loggeado ");
		}

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
		mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());
		mav.addObject("specialtyList", searchService.listSpecialtiesWithDoctors().get());
		mav.addObject("sexList", compressedSearch.get().getSex());
		mav.addObject("insuranceNameList", compressedSearch.get().getInsurance());
		mav.addObject("previousSearch", theSearch);

		return mav;
	}

	@RequestMapping("/specialist/{doctorId}")
    public ModelAndView doctorDescription(@PathVariable Integer doctorId, @ModelAttribute("search") Search search){

		final ModelAndView mav = new ModelAndView("specialist");


	    Doctor doctor = doctorService.findDoctorById(doctorId).get();
//		doctor.getDescription().getCertificate().remove(null);
		doctor.getDescription().getLanguages().remove(null);
//		doctor.getDescription().getEducation().remove(null);


		mav.addObject("doctor", doctor);
		mav.addObject("insuranceNameList", doctor.getInsurance());
        mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());

	    return mav;
    }

}
