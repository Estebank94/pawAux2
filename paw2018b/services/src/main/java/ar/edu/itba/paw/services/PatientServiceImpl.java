package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws IllegalArgumentException {
        LOGGER.debug("PatientServiceImpl: createPatient");
        if (firstName == null){
            LOGGER.debug("First Name is null");
            throw new IllegalArgumentException("firstName can't be null");
        }

        if (firstName.length() == 0){
            LOGGER.debug("First Name of a Patient has 0 characters");
            throw new IllegalArgumentException("Username firstname can't be empty");
        }

        if (firstName.length() > 45){
            LOGGER.debug("First Name of a Patient is longer than 45 characters. Name given: {}", firstName);
            throw new IllegalArgumentException("Username firstname maxlength is 50");
        }

        if (lastName == null) {
            LOGGER.debug("Last Name of a Patient is null");
            throw new IllegalArgumentException("last name can't be null");
        }
        if (lastName.length() == 0){
            LOGGER.debug("Last Name of a Patient has 0 characters");
            throw new IllegalArgumentException("Username Lastname can't be empty");
        }

        if (lastName.length() > 45){
            LOGGER.debug("Last Name longer than 45 characters. Last Name: {}", lastName);
            throw new IllegalArgumentException("Username Lastname maxlength is 45");
        }

        if (phoneNumber == null) {
            LOGGER.debug("UserName is null");
            throw new IllegalArgumentException("Username phonenumber can't be null");
        }
        if (phoneNumber.length() == 0){
            LOGGER.debug("PhoneNumber has 0 characters");
            throw new IllegalArgumentException("Username Phonenumber can't be empty");
        }

        if (phoneNumber.length() > 20){
            LOGGER.debug("Phone Number has more than 20 characters. Phone Numbe given is: {}", phoneNumber);
            throw new IllegalArgumentException("phonenumber can't have more than 20 characters");
        }

        if (email == null) {
            LOGGER.debug("Email is null");
            throw new IllegalArgumentException("email can't be null");
        }
        if (email.length() > 90){
            LOGGER.debug("Email has more than 90 characters. Email given: {}", email);
            throw new IllegalArgumentException("Email can't have more than 90 characters");
        }

        if (password == null){
            LOGGER.debug("Password is empty");
            throw new IllegalArgumentException("password can't be null");
        }

        if (password.length() == 0){
            LOGGER.debug("Password is empty");
            throw new IllegalArgumentException("password can't be empty");
        }
        if (password.length() > 72){
            LOGGER.debug("Password has more than 72 characters. Password given: {}", password);
            throw new IllegalArgumentException("password can't have more than 72 characters");
        }

        try{

            LOGGER.debug("patientDao.findPatientByEmail(email) with email: {}", email);
            Optional<Patient> patient = patientDao.findPatientByEmail(email);
            if(patient.isPresent()){
                LOGGER.debug("Patient not found. Patient email: {}", email);
                throw new IllegalArgumentException("Username with email " + email + " already exists");
            }
        }
        catch (NotFoundException e){

        }
        String finalpassword = passwordEncoder.encode(password);
        LOGGER.debug("patientDao.createPatient(firstName, lastName, phoneNumber, email, password)");
        LOGGER.debug("First Name: {}", firstName);
        LOGGER.debug("Last Name: {}", lastName);
        LOGGER.debug("Phone Number: {}", phoneNumber);
        LOGGER.debug("Email: {}", email);
        LOGGER.debug("Password: {}", finalpassword);
        Patient patient = patientDao.createPatient(firstName, lastName, phoneNumber, email, finalpassword);


        if (patient == null) {
            LOGGER.debug("Error creating Patient");
            throw new IllegalArgumentException("Error on create patient");
        }
        LOGGER.debug("Success. Created patient");
        return patient;
    }

    @Override
    public Boolean setDoctorId(Integer patientId, Integer doctorId) {
        LOGGER.debug("PatientServiceImpl: setDoctorId");
        if (patientId == null){
            LOGGER.debug("Patient ID: {} not found", patientId);
            throw new IllegalArgumentException("patientId can't be null");
        }
        if (patientId <= 0){
            LOGGER.debug("Patient ID: {} is negative", patientId);
            throw new IllegalArgumentException("PatientId can't be negative or zero");
        }
        if(!patientDao.findPatientById(patientId).isPresent()){
            LOGGER.debug("Patient not found");
            throw new NotFoundException("Patient was not found");
        }
        if (doctorId == null){
            LOGGER.debug("Doctor ID is null");
            throw new IllegalArgumentException("DoctorId can't be null");
        }
        if (doctorId <= 0){
            LOGGER.debug("Doctor ID is negative. ID given: {}", doctorId);
            throw new IllegalArgumentException("DoctorId can't be negative or zero");
        }
        LOGGER.debug("Calling: doctorDao.findDoctorById(patientId).isPresent()");
        LOGGER.debug("patientID: {}", patientId);
        if(!doctorDao.findDoctorById(patientId).isPresent()){
            LOGGER.debug("Doctor with ID: {} not found", patientId);
            throw new NotFoundException("Doctor was not found");
        }

        LOGGER.debug("Calling patientDao.setDoctorId(patientId, doctorId)");
        LOGGER.debug("patientID: {}", patientId);
        LOGGER.debug("doctorId {}", doctorId);
        return patientDao.setDoctorId(patientId, doctorId);
    }

    @Override
    public Patient findPatientById(Integer id) {
        LOGGER.debug("PatientServiceImpl: findPatientById");
        if (id == null){
            LOGGER.debug("Patient ID null");
            throw new IllegalArgumentException("patientId can't be null");
        }
        if (id <= 0){
            LOGGER.debug("Patient ID is negative. Patient ID given: {}", id);
            throw new IllegalArgumentException("PatientId can't be negative or zero");
        }
        LOGGER.debug("find patient by id. ID {}", id);
        Patient patient = patientDao.findPatientById(id).get();

        if (patient == null){
            LOGGER.debug("Patient not found");
            throw new NotFoundException("Patient was not found");
        }
        LOGGER.debug("Patient {}", patient);
        return patient;
    }

    @Override
    public Patient findPatientByEmail(String email) {
        LOGGER.debug("PatientServiceImpl: findPatientByEmail(String email)");
        if (email == null){
            LOGGER.debug("Email is null");
            throw new IllegalArgumentException("patient email can't be null");
        }
        if (email.length() == 0){
            LOGGER.debug("Email length is 0");
            throw new IllegalArgumentException("Patient email can't be negative or zero");
        }
        if (email.length() > 90){
            LOGGER.debug("Email has more than 90 characters. Email: {}", email);
            throw new IllegalArgumentException("PatientMail can't have more than 90 characters");
        }
        LOGGER.debug("Calling patientDao.findPatientByEmail(email)");
        Optional<Patient> foundPatient = patientDao.findPatientByEmail(email);
        if (!foundPatient.isPresent()){
            LOGGER.debug("No patient found");
            throw new IllegalArgumentException("Patient was not found");
        }
        LOGGER.debug("Patient found. Patient: {}", foundPatient.get());
        LOGGER.debug("Patient name: {}", foundPatient.get().getFirstName());
        return foundPatient.get();
        
    }

}
