package com.nvrignite.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;

import com.nvrignite.entities.User;

@RepositoryConfig(cacheName = "USER_CACHE")
public interface UserRepository extends IgniteRepository<User, String> {

	User findByUserName(String userName);

	
}