package ro.irian.cabinetveterinarapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ro.irian.*"})
@EntityScan(basePackages = {"ro.irian.*"})
public class CabinetVeterinarAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(CabinetVeterinarAppApplication.class, args);
	}

}
