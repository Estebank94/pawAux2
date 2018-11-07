package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import org.hsqldb.jdbc.JDBCDriver;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Properties;

import static org.mockito.Mockito.when;

@ComponentScan({ "ar.edu.itba.paw.services", })
@Configuration
public class TestConfig {

    public TestConfig() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private Doctor doctor;

    @Mock
    private Patient patient;

    @Mock
    private Appointment appointment;

    @Mock
    private AppointmentDao appointmentDao;

    @Mock
    private DoctorDao doctorDao;

    @Mock
    private SpecialtyDao specialtyDao;

    @Mock
    private InsurancePlanDao insurancePlanDao;

    @Mock
    private DescriptionDao descriptionDao;

    @Mock
    private WorkingHoursDao workingHoursDao;

    @Mock
    private FavoriteDao favoriteDao;

    @Mock
    private PatientDao patientDao;

    @Mock
    private ReviewDao reviewDao;

    @Mock
    private SearchDao searchDao;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Doctor getDoctor() {
        return doctor;
    }

    @Bean
    public Patient getPatient() {
        return patient;
    }

    @Bean
    public Appointment getAppointment() {
        return appointment;
    }

    @Bean
    public DoctorDao getDoctorDao() {
        return doctorDao;
    }

    @Bean
    public AppointmentDao getAppointmentDao() throws Exception{
        return appointmentDao;
    }

    @Bean
    public SpecialtyDao getSpecialtyDao() {
        return specialtyDao;
    }

    @Bean
    public InsurancePlanDao getInsurancePlanDao() {
        return insurancePlanDao;
    }

    @Bean
    public DescriptionDao getDescriptionDao() {
        return descriptionDao;
    }

    @Bean
    public WorkingHoursDao getWorkingHoursDao() {
        return workingHoursDao;
    }

    @Bean
    public FavoriteDao getFavoriteDao() {
        return favoriteDao;
    }

    @Bean
    public PatientDao getPatientDao() {
        return patientDao;
    }

    @Bean
    public ReviewDao getReviewDao() {
        return reviewDao;
    }

    @Bean
    public SearchDao getSearchDao() {
        return searchDao;
    }

    @Bean
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
        ds.setPassword("");
        return ds;
    }

    @Bean
    PasswordEncoder getEncoder() {
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        when(passwordEncoder.encode("SecretPass")).thenReturn("SecretPass");
        return passwordEncoder;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("waldocInc@gmail.com");
        mailSender.setPassword("waldoc2018");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return mailSender;
    }

}
