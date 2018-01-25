package com.binwang.frontOfBinwang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;





@EnableCaching
@SpringBootApplication
public class FrontOfBinwangApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(FrontOfBinwangApplication.class);
	}
//	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//		TomcatEmbeddedServletContainerFactory factory =
//				new TomcatEmbeddedServletContainerFactory();
//		return factory;
//	}


	public static void main(String[] args) {
		SpringApplication.run(FrontOfBinwangApplication.class, args);
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
//		container.setPort(8075);
	}  //	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//
//		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//		return factory;
//
//	}
//	public static void main(String[] args) {
//		SpringApplication.run(FrontOfBinwangApplication.class, args);
//	}
}
