package nl.brothersome.ecm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfiguration {

	@Autowired
	Environment env;

	private String basePath;

	@Bean
	public String getUser() {
		return user;
	}

	@Bean
	public String getPassword() {
		return password;
	}

	private String user;
	private String password;

	@Bean
	public LdapContextSource contextSource () {
		System.out.println("getting contextSource begin");
		LdapContextSource contextSource= new LdapContextSource();
		contextSource.setUrl(env.getRequiredProperty("ldap.url"));
		basePath = env.getRequiredProperty("ldap.base");
		contextSource.setBase(basePath);

		user = env.getProperty("ldap.user");
		if (user != null) {

			contextSource.setUserDn(user);
			password = env.getRequiredProperty("ldap.password");
			contextSource.setPassword(password);
		}
		System.out.println("URL:" + env.getRequiredProperty("ldap.url"));
		System.out.println("getting contextSource end");
		return contextSource;
	}

	@Bean
	public LdapContextSource getContextSource () {
		return contextSource();
	}

	@Bean
	public String getBasePath() {
		return basePath;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(contextSource());
	}

}
