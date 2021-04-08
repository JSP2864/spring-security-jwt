package com.example.spring.securityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

@SpringBootApplication
public class SpringSecurityJwtApplication {

		@Inject
		private Environment env;



		@PostConstruct
		public void initApplication() throws IOException {
			if (env.getActiveProfiles().length == 0) {
//			log.warn("No Spring profile configured, running with default configuration");
			} else {
//			log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
			}
		}

//	public static void main(String[] args) {
//		SpringApplication.run(SpringSecurityJwtApplication.class, args);
//	}


	/**
     * Main method, used to run the application.
     *
     * To run the application with hot reload enabled, add the following arguments to your JVM:
     * "-javaagent:spring_loaded/springloaded-jhipster.jar -noverify -Dspringloaded=plugins=io.github.jhipster.loaded.instrument.JHipsterLoadtimeInstrumentationPlugin"
     */
		public static void main(String[] args) {
			SpringApplication app = new SpringApplication(SpringSecurityJwtApplication.class);
//		app.setShowBanner(false);


			SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

			// Check if the selected profile has been set as argument.
			// if not the development profile will be added
			addDefaultProfile(app, source);

			app.run(args);
		}

		/**
		 * Rest template configuration with 5sec timeout
		 */
		@Bean
		public RestTemplate restTemplate() {
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			factory.setConnectTimeout(10000);
			factory.setReadTimeout(10000);
			return new RestTemplate(factory);
		}
		/**
		 * Set a default profile if it has not been set
		 */
		private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
			if (!source.containsProperty("spring.profiles.active")) {
				app.setAdditionalProfiles("dev");
			}
		}
	}






