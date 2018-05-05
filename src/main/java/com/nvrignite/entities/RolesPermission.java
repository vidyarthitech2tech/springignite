package com.nvrignite.entities;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class RolesPermission implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 572257273198993868L;

	private String permission;

	@QuerySqlField(index = true)
	private String roleName;


	public String getPermission() {
		return permission;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
