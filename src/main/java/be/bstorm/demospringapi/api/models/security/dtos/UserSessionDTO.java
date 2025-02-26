package be.bstorm.demospringapi.api.models.security.dtos;

import be.bstorm.demospringapi.dl.entities.User;
import be.bstorm.demospringapi.dl.enums.UserRole;

public record UserSessionDTO(
        Long id,
        UserRole role,
        String fullName
) {

    public static UserSessionDTO fromUser(User user) {
        String fullName = user.getLastName() + " " + user.getFirstName();
        return new UserSessionDTO(user.getId(),user.getRole(),fullName);
    }
}
