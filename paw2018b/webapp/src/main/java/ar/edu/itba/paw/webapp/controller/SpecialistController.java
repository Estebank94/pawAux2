package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by estebankramer on 26/09/2018.
 */

@Controller
public class SpecialistController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialistController.class);

    @Autowired
    private DoctorService doctorService;

}
