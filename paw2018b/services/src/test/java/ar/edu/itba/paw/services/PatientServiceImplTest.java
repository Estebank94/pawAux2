package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.models.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

//@Sql("classpath:ServiceTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PatientServiceImplTest {

//    private static final String PATIENT_EMAIL = "Roberto@rosa.com";
//    private static final Integer PATIENT_ID = 5;
//    private static final Integer PATIENT_DOC_ID = 1;

    private static final String NEW_PATIENT_NAME = "Mike";
    private static final String NEW_PATIENT_LASTNAME = "Muchpain";
    private static final String NEW_PATIENT_PHONE = "1512341234";
    private static final String NEW_PATIENT_EMAIL = "mike@lovewaldoc.com";
    private static final String NEW_PATIENT_PASSWORD = "SecretPass";

    private Patient createPatient;

    @Autowired
    private PatientServiceImpl patientServiceImpl;

    @Autowired
    private PatientDao patientDao;

    @Test
    public void testCreatePatient() throws Exception {

        createPatient = Mockito.mock(Patient.class);
        when(patientDao.createPatient(NEW_PATIENT_NAME, NEW_PATIENT_LASTNAME, NEW_PATIENT_PHONE, NEW_PATIENT_EMAIL, NEW_PATIENT_PASSWORD)).thenReturn(createPatient);
        when(createPatient.getFirstName()).thenReturn(NEW_PATIENT_NAME);
        when(createPatient.getLastName()).thenReturn(NEW_PATIENT_LASTNAME);
        when(createPatient.getPhoneNumber()).thenReturn(NEW_PATIENT_PHONE);
        when(createPatient.getEmail()).thenReturn(NEW_PATIENT_EMAIL);
        when(createPatient.getPassword()).thenReturn(NEW_PATIENT_PASSWORD);

        Patient newPatient = patientServiceImpl.createPatient(NEW_PATIENT_NAME, NEW_PATIENT_LASTNAME, NEW_PATIENT_PHONE, NEW_PATIENT_EMAIL, NEW_PATIENT_PASSWORD);

        assertNotNull(newPatient);
        assertEquals(NEW_PATIENT_NAME, newPatient.getFirstName());
        assertEquals(NEW_PATIENT_LASTNAME, newPatient.getLastName());
        assertEquals(NEW_PATIENT_PHONE, newPatient.getPhoneNumber());
        assertEquals(NEW_PATIENT_EMAIL, newPatient.getEmail());
        assertEquals(NEW_PATIENT_PASSWORD, newPatient.getPassword());

    }


}
