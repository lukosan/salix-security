package org.lukosan.salix.autoconfigure;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lukosan.salix.security.SalixSecurityProperties;
import org.lukosan.salix.security.ScopePermissionEvaluator;
import org.lukosan.salix.security.UserDetailsServiceAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@ConditionalOnProperty(prefix = "salix.security", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(SalixSecurityProperties.class)
public class SalixSecurityAutoConfiguration {

	@Configuration
	public static class UserDetailsServiceConfiguration {
		
		private static final Log logger = LogFactory.getLog(UserDetailsServiceConfiguration.class);
		
		@Bean
		@ConditionalOnMissingBean
		public UserDetailsService userDetailsService() {
			return new UserDetailsServiceAdapter();
		}
		
		@PostConstruct
		public void postConstruct() {
			if(logger.isInfoEnabled())
				logger.info("PostConstruct " + getClass().getSimpleName());
		}
	}
	
	@Configuration
	@ConditionalOnWebApplication
	@ComponentScan(basePackages="org.lukosan.salix.security")
	public static class SalixLoginHandlers {

		private static final Log logger = LogFactory.getLog(SalixLoginHandlers.class);
		
		@Bean
		public PermissionEvaluator permissionEvaluator() {
			return new ScopePermissionEvaluator();
		}
		
		@PostConstruct
		public void postConstruct() {
			if(logger.isInfoEnabled())
				logger.info("PostConstruct " + getClass().getSimpleName());
		}

	}

}