package com.nvrignite.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;

import com.nvrignite.entities.RolesPermission;

@RepositoryConfig(cacheName = "ROLES_CACHE")
public interface RolePermissionRepository extends IgniteRepository<RolesPermission, String> {

	RolesPermission findByRoleName(String roleName);

}