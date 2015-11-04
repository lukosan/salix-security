package org.lukosan.salix.security;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class ScopePermissionEvaluator implements PermissionEvaluator {

	private static final Log logger = LogFactory.getLog(ScopePermissionEvaluator.class);
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

		if(logger.isTraceEnabled())
			logger.trace("ScopePermissionEvaluator.hasPermission called on " + targetDomainObject.toString() + " for " + authentication.toString() + " to have permission " + permission.toString() );
		
		SalixUser user = UserDetailsAdapter.userFromAuthentication(authentication);
		if(null != user)
			return user.hasRole("*shared", "SALIX_ADMIN") || user.hasRole(targetDomainObject.toString(), "SALIX_ADMIN")
					|| user.hasRole("*shared", permission.toString()) || user.hasRole(targetDomainObject.toString(), permission.toString());
		
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		logger.warn("ScopePermissionEvaluator.hasPermission called for " + targetId + ", " + targetType + " with no implementation"); 
		return false;
	}

}