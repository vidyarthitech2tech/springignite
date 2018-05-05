package com.nvrignite.security;

import java.sql.Connection;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nvrignite.entities.RolesPermission;
import com.nvrignite.entities.User;
import com.nvrignite.repository.RolePermissionRepository;
import com.nvrignite.repository.UserRepository;

@Component
public class NvrJdbcRealm extends JdbcRealm {

	protected static UserRepository userRepository = null;
	protected static RolePermissionRepository rolePermissionRepository = null;

	public NvrJdbcRealm() {
		setName("NvrJdbcRealm");
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userRepository.findByUserName(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
		} else {
			return null;
		}
	}

	@Override
	protected Set<String> getRoleNamesForUser(Connection conn, String userName) {
		User user = userRepository.findByUserName(userName);
		return user.getUserRoles();
	}

	@Override
	protected Set<String> getPermissions(Connection conn, String userName, Collection<String> roleNames) {
		Set<String> permissions = new LinkedHashSet<>();
		RolesPermission role = new RolesPermission();
		role.setPermission("*");
		role.setRoleName("admin");
		rolePermissionRepository.save(role.getRoleName(), role);

		if (!roleNames.isEmpty()) {
			for (String roleName : roleNames) {
				RolesPermission roles = rolePermissionRepository.findByRoleName(roleName);
				permissions.add(roles.getPermission());
			}
		}

		return permissions;
	}

	/**
	 * This implementation of the interface expects the principals collection to
	 * return a String username keyed off of this realm's {@link #getName()
	 * name}
	 *
	 * @see #getAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// null usernames are invalid
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}

		String username = (String) getAvailablePrincipal(principals);

		Set<String> roleNames = null;
		Set<String> permissions = null;

		// Retrieve roles and permissions from database
		roleNames = getRoleNamesForUser(null, username);

		if (permissionsLookupEnabled) {
			permissions = getPermissions(null, username, roleNames);
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
		info.setStringPermissions(permissions);
		return info;

	}

	// setters

	// setters
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		NvrJdbcRealm.userRepository = userRepository;
	}

	@Autowired
	public void setRolePermissionRepository(RolePermissionRepository rolePermissionRepository) {
		NvrJdbcRealm.rolePermissionRepository = rolePermissionRepository;
	}

}
