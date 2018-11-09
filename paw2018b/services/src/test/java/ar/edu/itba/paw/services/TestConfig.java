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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
//
//    @Mock
//    private EntityManager em;
//
//    @Mock
//    private PlatformTransactionManager platformTransactionManager;

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
    public AppointmentDao getAppointmentDao() {
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
//
//    @Bean
//    public  EntityManager getEntityManager(){
//        return em;
//    }
//
//    @Bean
//    public PlatformTransactionManager getPlatformTransactionManager() {
//        return platformTransactionManager;
//    }


//    @Bean
//    public DataSource dataSource() {
//        final SimpleDriverDataSource ds = Mockito.mock(SimpleDriverDataSource.class);
//        return ds;
//    }

//    @Bean
//    public DataSource dataSource() {
//        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
//        ds.setDriverClass(JDBCDriver.class);
//        ds.setUrl("jdbc:hsqldb:mem:paw");
//        ds.setUsername("ha");
//        ds.setPassword("");
//        return ds;
//    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
//        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
//        factoryBean.setDataSource(dataSource());
//        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        factoryBean.setJpaVendorAdapter(vendorAdapter);
//        final Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "none");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        // Si ponen esto en prod, hay tabla!!!
//        properties.setProperty("hibernate.show_sql", "true");
//        properties.setProperty("format_sql", "true");
//        factoryBean.setJpaProperties(properties);
//        return factoryBean;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(
//            final EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }

    @Bean
    PasswordEncoder getEncoder() {
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        when(passwordEncoder.encode("SecretPass")).thenReturn("SecretPass");
        return passwordEncoder;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSender mailSender = Mockito.mock(JavaMailSender.class);
        return mailSender;
    }

}
