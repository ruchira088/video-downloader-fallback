package com.ruchij.services.authentication;

import com.ruchij.daos.authorization.models.RoleType;
import com.ruchij.daos.user.models.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PermissionEvaluatorImpl implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        User user = (User) authentication.getPrincipal();
        String type = targetType.toLowerCase();

        if (user.getRoles().stream().anyMatch(role -> role.getRoleType() == RoleType.ROLE_ADMIN)) {
            return true;
        } else if (type.equals("user")) {
            return user.getId().equals(targetId);
        } else {
            return false;
        }
    }
}
