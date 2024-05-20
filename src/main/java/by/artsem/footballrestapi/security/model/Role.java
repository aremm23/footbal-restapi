package by.artsem.footballrestapi.security.model;

import java.util.Optional;

public enum Role {
    USER, ADMIN;
    public static Optional<Role> parseStringToRole(String roleStr) {
        if(roleStr.equalsIgnoreCase("user")){
            return Optional.of(USER);
        }
        else if (roleStr.equalsIgnoreCase("admin")){
            return Optional.of(ADMIN);
        }
        return Optional.empty();
    }
}
