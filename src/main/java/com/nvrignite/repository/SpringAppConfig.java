package com.nvrignite.repository;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.nvrignite.entities.RolesPermission;
import com.nvrignite.entities.User;

@Configuration
@ComponentScan(basePackages={"com.nvrignite.repository"})
@EnableIgniteRepositories
public class SpringAppConfig {


	@Bean
	public Ignite igniteInstance() {
		IgniteConfiguration cfg = new IgniteConfiguration();
		// Setting some custom name for the node.
		cfg.setIgniteInstanceName("sample");
		// Enabling peer-class loading feature.
		cfg.setPeerClassLoadingEnabled(true);

		CacheConfiguration userCache = new CacheConfiguration("UserCache");
		CacheConfiguration roleCache = new CacheConfiguration("RoleCache");

		userCache.setIndexedTypes(String.class, User.class);
		roleCache.setIndexedTypes(String.class, RolesPermission.class);
		cfg.setCacheConfiguration(new CacheConfiguration[] {userCache, roleCache});

		return Ignition.start(cfg);
	}
}