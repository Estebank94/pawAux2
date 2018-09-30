package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.AppointmentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class HelloWorldController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);
	
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

	@RequestMapping(value = "/specialist/{doctorId}", method = { RequestMethod.GET})
    public ModelAndView doctorDescription(@PathVariable Integer doctorId, @ModelAttribute("search") Search search,
										  @ModelAttribute("appointment")AppointmentForm appointmentForm){

		final ModelAndView mav = new ModelAndView("specialist");


	    Doctor doctor = doctorService.findDoctorById(doctorId).get();
		doctor.getDescription().getLanguages().remove(null);

		mav.addObject("doctor", doctor);
		mav.addObject("insuranceNameList", doctor.getInsurance());
		mav.addObject("appointmentsAvailable", doctor.getAvailableAppointments());
        mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());

	    return mav;
    }

    @RequestMapping(value = "/specialist/{doctorId}", method = {RequestMethod.POST})
	public ModelAndView doctorDescriptionPost(@PathVariable Integer doctorId, @ModelAttribute("search") Search search,
											  @ModelAttribute("appointment")AppointmentForm appointmentForm){
		System.out.println(appointmentForm.getDay());
		System.out.println(appointmentForm.getTime());
		return new ModelAndView("finalStep");
	}

}
