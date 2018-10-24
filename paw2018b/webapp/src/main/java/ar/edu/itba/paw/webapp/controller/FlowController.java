package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AppointmentService;
import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import ar.edu.itba.paw.webapp.forms.AppointmentForm;
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
import ar.edu.itba.paw.interfaces.PatientService;

import java.time.LocalDate;
import java.time.LocalTime;
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

	@RequestMapping("/processForm")
	public ModelAndView processForm(@ModelAttribute("search") Search theSearch) throws NotValidSearchException {
//		LOGGER.debug("Calling: ProcessForm");
//
		final ModelAndView mav = new ModelAndView("specialists");
//		Optional<CompressedSearch> compressedSearch;
//		compressedSearch = doctorService.findDoctors(theSearch);
//		List<Doctor> doctorsList = null;
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

		List<Doctor> doctor = doctorService.listDoctors();
		mav.addObject("doctorList", doctorService.listDoctors());
		mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());
		mav.addObject("specialtyList", searchService.listSpecialtiesWithDoctors().get());
//		mav.addObject("sexList", compressedSearch.get().getSex());
//		mav.addObject("insuranceNameList", compressedSearch.get().getInsurance());
		mav.addObject("previousSearch", theSearch);


		return mav;
	}

	@RequestMapping(value = "/specialist/{doctorId}", method = { RequestMethod.GET})
    public ModelAndView doctorDescription(@PathVariable Integer doctorId, @ModelAttribute("search") Search search,
										  @ModelAttribute("appointment") AppointmentForm appointmentForm){
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
				mav.addObject("doctor", doctor);
				mav.addObject("workingHoursTest", doctor.getWorkingHours());
//				TODO: AGREGAR FUNCION QUE BUSCA INSURANCE USANDO LOS INSURANCE PLANS
				mav.addObject("insuranceNameList", doctor.getInsurancePlans());
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
											  @ModelAttribute("appointment")AppointmentForm appointmentForm) throws NotFoundDoctorException, NotValidIDException {

		Doctor doctor = doctorService.findDoctorById(doctorId).get();

		/*TODO: check validation for try catch*/

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Patient patient = null;
		try {
			patient = patientService.findPatientByEmail(authentication.getName());
		} catch (NotValidEmailException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		} catch (NotFoundPacientException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		}
		try {
			appointmentService.createAppointment(appointmentForm.getDay(), appointmentForm.getTime(), patient, doctor);
		} catch (RepeatedAppointmentException e) {
			LOGGER.debug("The appointment has just been taken");
			return doctorDescription(doctorId,search,appointmentForm).addObject("appointmentTaken", true);
		} catch (NotCreatedAppointmentException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		} catch (NotValidDoctorIdException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		} catch (NotValidAppointmentDayException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		} catch (NotValidAppointmentTimeException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		} catch (NotValidPatientIdException e) {
			LOGGER.trace("404 error");
			return new ModelAndView("404");
		}

		ModelAndView mav = new ModelAndView("appointmentSuccess");
		mav.addObject("doctor", doctor);
		mav.addObject("appointmentDay", appointmentForm.getDay());
		mav.addObject("appointmentTime", appointmentForm.getTime());
		return mav;
	}

}
