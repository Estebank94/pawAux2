package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import ar.edu.itba.paw.webapp.forms.AppointmentForm;
import ar.edu.itba.paw.webapp.forms.FavoriteForm;
import ar.edu.itba.paw.webapp.forms.ReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;

@Controller
public class FlowController {
	/*
	private static final Logger LOGGER = LoggerFactory.getLogger(FlowController.class);

	 @Autowired
	 @Qualifier("patientServiceImpl")
	 private PatientService us;

	 @Autowired
	 private DoctorService doctorService;

	@Autowired
	private SearchService searchService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private InsuranceService insuranceService;

	@RequestMapping("/")
	public ModelAndView index() throws NotFoundDoctorException, NotValidIDException {
		LOGGER.debug("Starting Waldoc ... ");
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("search", new Search());
		mav.addObject("insuranceList", searchService.listInsurances());
		mav.addObject("specialtyList", searchService.listSpecialties());

		boolean hasUserRole = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			LOGGER.debug("There is an User Logged IN");
			hasUserRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals("ROLE_DOCTOR"));
			if(hasUserRole){
				Doctor doctor;
				Patient patient;
				try {
					patient = patientService.findPatientByEmail(authentication.getName());
					doctor = doctorService.findDoctorById(String.valueOf(patient.getDoctor().getId()));
					LOGGER.debug("The User Logged in is a DOCTOR with ID: {}", doctor.getId());
				}catch (NotFoundDoctorException ex1){
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				} /* catch (NotFoundPacientException e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				} catch (NotValidEmailException e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				}

				mav.addObject("doctorID", doctor.getId());
			}
			hasUserRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals("ROLE_PATIENT"));
			if(hasUserRole){
				Patient patient;
				patient = patientService.findPatientByEmail(authentication.getName());
				/*
				try {
					patient = patientService.findPatientByEmail(authentication.getName());
				} catch (NotFoundPacientException | NotValidEmailException e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				}


				mav.addObject("patient", patient);
				mav.addObject("hasFavorites", patient.getFavoriteDoctors().size() > 0 );
			}
		}
		return mav;
	}

	@RequestMapping("/processForm/{page}")
	public ModelAndView processForm(@ModelAttribute("search") Search search, @PathVariable("page") String page) throws NotValidSearchException {
		LOGGER.debug("Calling: ProcessForm");

		final ModelAndView mav = new ModelAndView("specialists");
		List<Doctor> doctorsList;
		boolean notFound = false;

		try	{
			doctorsList = doctorService.listDoctors(search, page);
			mav.addObject("totalPages", doctorService.getLastPage(search));
		} catch (Exception e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		}

		if(doctorsList.isEmpty()){
			doctorsList = doctorService.listDoctors(Integer.parseInt(page));
			notFound = true;
			mav.addObject("notFound", notFound);
			mav.addObject("totalPages", doctorService.getLastPage(new Search()));
		}


		boolean hasUserRole = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			hasUserRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals("ROLE_DOCTOR"));
			if(hasUserRole){
				Patient patient = null;
				patient = patientService.findPatientByEmail(authentication.getName());
				/*
				try {
					patient = patientService.findPatientByEmail(authentication.getName());
				} catch (NotValidEmailException | NotFoundPacientException e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				}


				mav.addObject("doctorID", patient.getDoctor().getId());
			}
		}

		List<String> sexes = new ArrayList<>();

		for(Doctor doctor : doctorsList){
			if(sexes.size() < 2 && !sexes.contains(doctor.getSex())){
				sexes.add(doctor.getSex());
			}
		}

		if(!search.getInsurance().equals("no")){
			mav.addObject("searchInsurance", insuranceService.getInsuranceByName(search.getInsurance()));
		}
		mav.addObject("currentPage", page);
		mav.addObject("doctorList", doctorsList);
		mav.addObject("sexes", sexes);
		mav.addObject("insurances", searchService.listInsurances());
		mav.addObject("specialtyList", searchService.listSpecialties());
		mav.addObject("qName", search.getName().replace(" ","+"));
		mav.addObject("qInsurance", search.getInsurance().replace(" ","+"));
		mav.addObject("qSpecialty", search.getSpecialty().replace(" ","+"));
		mav.addObject("qSex", search.getSex());
		mav.addObject("qDay", search.getDays());
		mav.addObject("futureDays", searchService.getFutureDays());

		return mav;
	}

	@RequestMapping(value = "/specialist/{doctorId}", method = { RequestMethod.GET})
    public ModelAndView doctorDescription(@PathVariable String doctorId, @ModelAttribute("search") Search search,
										  @ModelAttribute("appointment") AppointmentForm appointmentForm,
										  @ModelAttribute("review") ReviewForm reviewForm){

		LOGGER.debug("DoctorDesciption. DoctorID = {}", doctorId);
			final ModelAndView mav = new ModelAndView("specialist");
			try {
				Doctor doctor;
				try {
					doctor = doctorService.findDoctorById(doctorId);
					if (doctor == null){
						LOGGER.trace("404 error");
						return new ModelAndView("404");
					}
				} catch (Exception e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				}


				boolean hasUserRole = false;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!(authentication instanceof AnonymousAuthenticationToken)) {

					hasUserRole = authentication.getAuthorities().stream()
							.anyMatch(r -> r.getAuthority().equals("ROLE_PATIENT"));
					if(hasUserRole){
						Patient patient = patientService.findPatientByEmail(authentication.getName());
						mav.addObject("user", patient);
						mav.addObject("doctorID", doctor.getId());
						boolean isFavorite = patient.isFavorite(doctor);
						System.out.println(isFavorite);

					}
					hasUserRole = authentication.getAuthorities().stream()
							.anyMatch(r -> r.getAuthority().equals("ROLE_PATIENT"));
					if(hasUserRole){
						Patient patient = patientService.findPatientByEmail(authentication.getName());
						mav.addObject("user", patient);
						mav.addObject("isFavorite", patient.isFavorite(doctor));

					}
				}


				mav.addObject("doctor", doctor);
				if(doctor.getDescription() == null || doctor.getDescription().getLanguages() == null ||doctor.getDescription().getLanguages().matches("no")){
					mav.addObject("languangesNo", true);
				}else{
					mav.addObject("languagesNo", false);
				}

				mav.addObject("insuranceNameList", doctor.getInsurancePlans());
				List<WorkingHours> wh = doctor.getWorkingHours();
				Map<LocalDate, List<Appointment>> appointments = doctor.getAvailableAppointments();
				mav.addObject("appointmentsAvailable", doctor.getAvailableAppointments());
				mav.addObject("insuranceList", searchService.listInsurances());
				mav.addObject("specialtyList", searchService.listSpecialties());
				mav.addObject("appointmentTaken",false);
			} catch (NotFoundException e) {
				LOGGER.trace("404 error");
				return new ModelAndView("404");
			} /* catch (NotFoundPacientException e) {
				LOGGER.trace("404 error");
				return new ModelAndView("404");
			} catch (NotValidEmailException e) {

				LOGGER.trace("404 error");
				return new ModelAndView("404");
			}

		return mav;
    }

    @RequestMapping(value = "/specialist/{doctorId}", method = {RequestMethod.POST})
	public ModelAndView doctorDescriptionPost(@PathVariable Integer doctorId, @ModelAttribute("search") Search search,
											  @ModelAttribute("appointment")AppointmentForm appointmentForm,
											  @ModelAttribute("review")ReviewForm reviewForm,
											  @ModelAttribute("favorite") FavoriteForm favoriteForm)
											  throws NotFoundDoctorException, NotValidIDException, NotFoundPacientException,
												NotValidPatientIdException, NotCreatePatientException {

		Doctor doctor = doctorService.findDoctorById(String.valueOf(doctorId));
		boolean appointment = false;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Patient patient = null;
		patient = patientService.findPatientByEmail(authentication.getName());
		/*
		try {
			patient = patientService.findPatientByEmail(authentication.getName());
		} catch (NotValidEmailException | NotFoundPacientException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		}


		try {
			if(appointmentForm.getDay() != null && appointmentForm.getTime() != null) {
				appointment = true;
				appointmentService.createAppointment(appointmentForm.getDay(), appointmentForm.getTime(), patient, doctor);
			}
		} catch (RepeatedAppointmentException e) {
			LOGGER.debug("The appointment has just been taken");
			return doctorDescription(String.valueOf(doctorId),search,appointmentForm, reviewForm).addObject("appointmentTaken", true);
		} catch (NotCreatedAppointmentException | NotValidDoctorIdException | NotValidAppointmentDayException | NotValidAppointmentTimeException
				| NotValidPatientIdException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		}

		if(reviewForm.getStars() != null && reviewForm.getDescription() != null){
			Review review = null;

			try {
				review = reviewService.createReview(reviewForm.getStars(), reviewForm.getDescription(),doctor, patient.getFirstName(), patient.getLastName());
			} catch (NotValidReviewException e) {
				LOGGER.trace("404");
				return new ModelAndView("404");
			}
		}

		if(favoriteForm.getAction() != null){
			if(favoriteForm.getAction().equals("add")){
				try {
					favoriteService.addFavorite(doctor, patient);
				} catch (NotCreatedFavoriteException e) {
					LOGGER.trace("404 Error");
					return new ModelAndView("404");
				} catch (FavoriteExistsException e){
					///mandre frula
					return new ModelAndView("404");
				}

			} else {
				try {
					favoriteService.removeFavorite(doctor, patient);
				} catch (Exception e){
					LOGGER.trace("404 Error");
					return new ModelAndView("404");
				}
			}

		}


		ModelAndView mav;
		if(appointment){
			mav = new ModelAndView("appointmentSuccess");
			mav.addObject("doctor", doctor);
			mav.addObject("appointmentDay", appointmentForm.getDay());
			mav.addObject("appointmentTime", appointmentForm.getTime());
		}else{
			mav = new ModelAndView("redirect:/specialist/{doctorId}");
		}

		return mav;
	}


}


	 */
}