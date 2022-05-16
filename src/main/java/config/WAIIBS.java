package config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WAIIBS extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
	{
	return application.sources(WAIIBS.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(WAIIBS.class, args);
	}

}
