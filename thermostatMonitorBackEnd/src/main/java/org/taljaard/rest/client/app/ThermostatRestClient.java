package org.taljaard.rest.client.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="org.taljaard.rest.client")
public class ThermostatRestClient {

	public static void main(String[] args) {
		SpringApplication.run(ThermostatRestClient.class, args);
	}
}
