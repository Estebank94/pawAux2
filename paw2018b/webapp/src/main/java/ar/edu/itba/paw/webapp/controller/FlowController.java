package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import ar.edu.itba.paw.webapp.forms.AppointmentForm;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class FlowController {

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

	@RequestMapping("/")
	public ModelAndView index() throws NotFoundDoctorException, NotValidIDException {
		LOGGER.debug("Starting Waldoc ... ");
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("search", new Search());
		mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());
		mav.addObject("specialtyList", searchService.listSpecialtiesWithDoctors().get());

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
					doctor = doctorService.findDoctorById(patient.getDoctor().getId()).get();
					LOGGER.debug("The User Logged in is a DOCTOR with ID: {}", doctor.getId());
				}catch (NotFoundDoctorException ex1){
					LOGGER.trace("404 error");
					/*aca esta llegando cuando toco en el registrationController waldoc*/
					return new ModelAndView("404");
				} catch (NotFoundPacientException e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				} catch (NotValidEmailException e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				}
				mav.addObject("doctorID", doctor.getId());
			}
		}
		return mav;
	}

	@RequestMapping("/processForm/{page}")
	public ModelAndView processForm(@ModelAttribute("search") Search search, @PathVariable("page") Integer page) throws NotValidSearchException {
//		LOGGER.debug("Calling: ProcessForm");
//
		final ModelAndView mav = new ModelAndView("specialists");
//		Optional<CompressedSearch> compressedSearch;
//		compressedSearch = doctorService.findDoctors(theSearch);
		List<Doctor> doctorsList = doctorService.listDoctors(search, page);
//		if(compressedSearch.isPresent()) {
//			doctorsList = compressedSearch.get().getDoctors();
//		}
//		else {
//			doctorsList = doctorService.listDoctors();
//			mav.addObject("notFound", "no");
//			theSearch.setName("");
//			theSearch.setInsurance("no");
//			theSearch.setSpecialty("");
//		}
//		boolean hasUserRole = false;
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (!(authentication instanceof AnonymousAuthenticationToken)) {
//
//			hasUserRole = authentication.getAuthorities().stream()
//					.anyMatch(r -> r.getAuthority().equals("ROLE_DOCTOR"));
//			if(hasUserRole){
//				Patient patient = null;
//				try {
//					patient = patientService.findPatientByEmail(authentication.getName());
//				} catch (NotValidEmailException e) {
//					LOGGER.trace("404 error");
//					return new ModelAndView("404");
//				} catch (NotFoundPacientException e) {
//					LOGGER.trace("404 error");
//					return new ModelAndView("404");
//				}
//				mav.addObject("doctorID", patient.getDoctor().getId());
//			}
//		}
//
//		LOGGER.debug("GET DoctorList {}", doctorsList.toString());
//		LOGGER.debug("GET ListInsurance {}", searchService.listInsurancesWithDoctors().get().toString());
//		LOGGER.debug("GET specialtyList {}",  searchService.listSpecialtiesWithDoctors().get().toString());
//		LOGGER.debug("GET sexList {}", compressedSearch.get().getSex().toString());
//		LOGGER.debug("GET insuranceNameList {}", compressedSearch.get().getInsurance().toString());

		List<String> sex = new ArrayList<>();
		List<Insurance> insurance = new ArrayList<>();
		List<InsurancePlan> insurancePlans = new ArrayList<>();

		for(Doctor doctor : doctorsList){
			for(InsurancePlan plan : doctor.getInsurancePlans()){
				if(!insurance.contains(plan.getInsurance())){
					insurance.add(plan.getInsurance());
				}
			}
			if(sex.size() < 2 && !sex.contains(doctor.getSex())){
				sex.add(doctor.getSex());
			}
		}
		mav.addObject("totalPages", doctorService.getLastPage());
		mav.addObject("currentPage", page);
		mav.addObject("doctorList", doctorsList);
		mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());
		mav.addObject("specialtyList", searchService.listSpecialtiesWithDoctors().get());
		mav.addObject("sexList", sex);
		mav.addObject("previousSearch", search);

		return mav;
	}

	@RequestMapping(value = "/specialist/{doctorId}", method = { RequestMethod.GET})
    public ModelAndView doctorDescription(@PathVariable Integer doctorId, @ModelAttribute("search") Search search,
										  @ModelAttribute("appointment") AppointmentForm appointmentForm,
										  @ModelAttribute("review") ReviewForm reviewForm){
		LOGGER.debug("DoctorDesciption. DoctorID = {}", doctorId);
			final ModelAndView mav = new ModelAndView("specialist");
			try {
				Doctor doctor;
				try {
					doctor = doctorService.findDoctorById(doctorId).get();
				} catch (NotFoundDoctorException e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				} catch (NotValidIDException e) {
					LOGGER.trace("404 error");
					return new ModelAndView("404");
				}
//				if (doctor.getDescription() != null){
//					if(doctor.getDescription().getLanguages() == null){
//						doctor.getDescription().getLanguages().contains("no");
////					TODO buscar el no en el string y sacarlo. PARSERRRRR
////					doctor.getDescription().getLanguages().remove("no");
//					}
//				}

				boolean hasUserRole = false;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!(authentication instanceof AnonymousAuthenticationToken)) {

					hasUserRole = authentication.getAuthorities().stream()
							.anyMatch(r -> r.getAuthority().equals("ROLE_DOCTOR"));
					if(hasUserRole){
						Patient patient = patientService.findPatientByEmail(authentication.getName());
						mav.addObject("doctorID", doctor.getId());
					}
				}
//				Patient patient = patientService.findPatientByEmail(authentication.getName());
//				favoriteService.addFavorite(doctor, patient);

				mav.addObject("doctor", doctor);
//				mav.addObject("workingHoursTest", doctor.getWorkingHours());
//				TODO: AGREGAR FUNCION QUE BUSCA INSURANCE USANDO LOS INSURANCE PLANS
				mav.addObject("insuranceNameList", doctor.getInsurancePlans());
				List<WorkingHours> wh = doctor.getWorkingHours();
				Map<LocalDate, List<Appointment>> appointments = doctor.getAvailableAppointments();
				mav.addObject("appointmentsAvailable", doctor.getAvailableAppointments());
				mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());
				mav.addObject("specialtyList", searchService.listSpecialtiesWithDoctors().get());
				mav.addObject("appointmentTaken",false);
			} catch (NotFoundException e) {
				LOGGER.trace("404 error");
				return new ModelAndView("404");
			} catch (NotFoundPacientException e) {
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
												@ModelAttribute("review")ReviewForm reviewForm) throws NotFoundDoctorException, NotValidIDException {

		Doctor doctor = doctorService.findDoctorById(doctorId).get();

		/*TODO: check validation for try catch*/

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//		Patient patient = null;
//		try {
//			patient = patientService.findPatientByEmail(authentication.getName());
//		} catch (NotValidEmailException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		} catch (NotFoundPacientException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		}
//		try {
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//			//convert String to LocalDate
//			String day = appointmentForm.getDay();
//			LocalDate localDate = LocalDate.parse(appointmentForm.getDay(), formatter);
//			appointmentService.createAppointment(appointmentForm.getDay(), appointmentForm.getTime(), patient, doctor);
//		} catch (RepeatedAppointmentException e) {
//			LOGGER.debug("The appointment has just been taken");
//			return doctorDescription(doctorId,search,appointmentForm, reviewForm).addObject("appointmentTaken", true);
//		} catch (NotCreatedAppointmentException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		} catch (NotValidDoctorIdException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		} catch (NotValidAppointmentDayException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		} catch (NotValidAppointmentTimeException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		} catch (NotValidPatientIdException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		}

//		TODO fix null from form
		Review review = new Review(5, "Muy Bueno!", doctor, "hola", "hola");
		reviewService.createReview(review);


		ModelAndView mav = new ModelAndView("appointmentSuccess");
		mav.addObject("doctor", doctor);
//		mav.addObject("appointmentDay", appointmentForm.getDay());
//		mav.addObject("appointmentTime", appointmentForm.getTime());
		return mav;
	}

//	@RequestMapping(value = "/specialist/{doctorId}", method = {RequestMethod.POST})
//	public ModelAndView doctorReviewPost(@PathVariable Integer doctorId, @ModelAttribute("search") Search search,
//											  @ModelAttribute("review")ReviewForm reviewForm) throws NotFoundDoctorException, NotValidIDException {
//
//		Doctor doctor = doctorService.findDoctorById(doctorId).get();
//
//		/*TODO: check validation for try catch*/
//
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		Patient patient = null;
//		try {
//			patient = patientService.findPatientByEmail(authentication.getName());
//		} catch (NotValidEmailException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		} catch (NotFoundPacientException e) {
//			LOGGER.trace("404 error");
//			return new ModelAndView("404");
//		}
//
//
//		Review review = new Review(reviewForm.getStars(), reviewForm.getDescription(), doctor, patient.getFirstName(), patient.getLastName());
//		reviewService.createReview(review);
//
//
/////		Hacer que te haga refresh la pagina
//		ModelAndView mav = new ModelAndView("appointmentSuccess");
//		mav.addObject("doctor", doctor);
//		return mav;
//	}

}
