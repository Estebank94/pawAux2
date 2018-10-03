package ar.edu.itba.paw.webapp.config;


import ar.edu.itba.paw.interfaces.FileUploadDao;
import ar.edu.itba.paw.interfaces.FileUploadService;
import org.apache.commons.fileupload.FileUpload;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.webapp.controller", "ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence" })
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("/resources/schema.sql")
	private Resource schemaSQL;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private FileUpload fileUpload;

	@Bean
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");
	}

	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(org.postgresql.Driver.class);
		ds.setUrl("jdbc:postgresql://localhost:9091/");
		//ds.setUrl("jdbc:postgresql://10.16.1.110:5432/paw-2018b-06");
		ds.setUsername("paw-2018b-06");
		ds.setPassword("67wFYxljg");
		return ds;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
		final DataSourceInitializer dsi = new DataSourceInitializer();
		dsi.setDataSource(ds);
		dsi.setDatabasePopulator(databasePopulator());
		return dsi;
	}

	private DatabasePopulator databasePopulator(){
		final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
		dbp.addScript(schemaSQL);
		return dbp;
	}

	@Bean
	public MessageSource messageSource (){
		final ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasename("/resources/i18n/messages");
		source.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		source.setCacheSeconds(5);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider(){
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(
				new String[] { "ar.edu.itba.paw"  }
		);

		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}


	@Autowired
	@Bean(name = "fileUploadDao")
	public FileUploadDao getUserDao(SessionFactory sessionFactory) {
		return new FileUploadService(sessionFactory);
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(2097152);   // 2MB
		multipartResolver.setMaxInMemorySize(1048576);  // 1MB
		return multipartResolver;
	}
}

