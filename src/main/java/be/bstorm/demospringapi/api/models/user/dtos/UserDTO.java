package be.bstorm.demospringapi.api.models.user.dtos;

import be.bstorm.demospringapi.dl.entities.User;

public record UserDTO(
        Long id,
        String fullName
) {

    public static UserDTO fromUser(User user) {
        String fullName = user.getLastName() + " " + user.getFirstName();
        return new UserDTO(user.getId(),fullName);
    }
}
