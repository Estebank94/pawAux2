package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws IllegalArgumentException {

        if (firstName == null){
            throw new IllegalArgumentException("firstName can't be null");
        }

        if (firstName.length() == 0){
            throw new IllegalArgumentException("Doctor firstname can't be empty");
        }

        if (firstName.length() > 45){
            throw new IllegalArgumentException("Doctor firstname maxlength is 50");
        }

        if (lastName == null) {
            throw new IllegalArgumentException("last name can't be null");
        }
        if (lastName.length() == 0){
            throw new IllegalArgumentException("Doctor firstname can't be empty");
        }

        if (lastName.length() > 45){
            throw new IllegalArgumentException("Doctor firstname maxlength is 50");
        }

        if (phoneNumber == null) {
            throw new IllegalArgumentException("phonenumber can't be null");
        }
        if (phoneNumber.length() == 0){
            throw new IllegalArgumentException("phonenumber firstname can't be empty");
        }

        if (phoneNumber.length() > 20){
            throw new IllegalArgumentException("phonenumber can't have more than 20 characters");
        }

        if (email == null) {
            throw new IllegalArgumentException("sex can't be null");
        }
        if (email.length() > 90){
            throw new IllegalArgumentException("Email can't have more than 90 characters");
        }

        if(patientDao.findPatientByEmail(email).isPresent()){
            throw new IllegalArgumentException("Username with email " + email + " already exists");
        }

        if (password.length() == 0){
            throw new IllegalArgumentException("password can't be empty");
        }

        if (password.length() > 72){
            throw new IllegalArgumentException("password can't have more than 1 characters");
        }
        Patient patient = patientDao.createPatient(firstName, lastName, phoneNumber, email, password);
        if (patient == null) {
            throw new IllegalArgumentException("Error on create patient");
        }
        return patient;
    }

    @Override
    public Boolean setDoctorId(Integer patientId, Integer doctorId) {
        if (patientId == null){
            throw new IllegalArgumentException("patientId can't be null");
        }
        if (patientId <= 0){
            throw new IllegalArgumentException("PatientId can't be negative or zero");
        }
        if(!patientDao.findPatientById(patientId).isPresent()){
            throw new NotFoundException("Patient was not found");
        }
        if (doctorId == null){
            throw new IllegalArgumentException("patientId can't be null");
        }
        if (doctorId <= 0){
            throw new IllegalArgumentException("PatientId can't be negative or zero");
        }
        if(!doctorDao.findDoctorById(patientId).isPresent()){
            throw new NotFoundException("Patient was not found");
        }

        return patientDao.setDoctorId(patientId, doctorId);
    }

    @Override
    public Patient findPatientById(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("patientId can't be null");
        }
        if (id <= 0){
            throw new IllegalArgumentException("PatientId can't be negative or zero");
        }
        Patient patient = patientDao.findPatientById(id).get();

        if (patient == null){
            throw new NotFoundException("Patient was not found");
        }
        return patient;
    }

    @Override
    public Patient findPatientByEmail(String email) {
        if (email == null){
            throw new IllegalArgumentException("patient email can't be null");
        }
        if (email.length() == 0){
            throw new IllegalArgumentException("Patient email can't be negative or zero");
        }
        if (email.length() > 90){
            throw new IllegalArgumentException("PatientMail can't have more than 90 characters");
        }
        Optional<Patient> foundPatient = patientDao.findPatientByEmail(email);
        if (!foundPatient.isPresent()){
            throw new IllegalArgumentException("Patient was not found");
        }
        return foundPatient.get();
        
    }

}
