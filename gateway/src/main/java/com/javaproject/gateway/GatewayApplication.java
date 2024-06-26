package com.javaproject.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.Date;


@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/awbd/order/**")
						.filters(f -> f.rewritePath("/awbd/order/(?<segment>.*)", "/${segment}"))
						.uri("lb://ORDER"))
				.route(p -> p
						.path("/awbd/promotion/**")
						.filters(f -> f.rewritePath("/awbd/promotion/(?<segment>.*)", "/${segment}"))
						.uri("lb://PROMOTION"))
				.build();
	}

//	@Bean
//	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(p -> p
//						.path("/awbd/order/**")
//						.filters(f -> f.rewritePath("/awbd/order/(?<segment>.*)", "/${segment}")
//								.addResponseHeader("X-Response-Time",new Date().toString()))
//						.uri("lb://ORDER"))
//				.route(p -> p
//						.path("/awbd/promotion/**")
//						.filters(f -> f.rewritePath("/awbd/promotion/(?<segment>.*)", "/${segment}")
//								.addResponseHeader("X-Response-Time",new Date().toString()))
//						.uri("lb://PROMOTION"))
//				.build();
//	}


}
