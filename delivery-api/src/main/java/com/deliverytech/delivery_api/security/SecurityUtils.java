package com.deliverytech.delivery_api.security;

import com.deliverytech.delivery_api.entity.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {
    
     private SecurityUtils() {
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUsername() {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else if (principal instanceof String s) {
            return s;
        }

        return null;
    }

    public static Long getCurrentUserId() {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof Usuario usuario) {
            return usuario.getId();
        }

        return null;
    }

    public static boolean hasRole(String role) {
        Authentication auth = getAuthentication();
        if (auth == null || auth.getAuthorities() == null) {
            return false;
        }

        return auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
}
