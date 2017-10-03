package com.OtauBanco;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
    public static void main(String[] args) throws Exception{
        
    	SpringApplication.run(Application.class, args);
    }
}

/*
 * spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://sql10.freemysqlhosting.net/sql10197603
spring.datasource.username=sql10197603
spring.datasource.password=Q4sbq9HhTE
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
*/
