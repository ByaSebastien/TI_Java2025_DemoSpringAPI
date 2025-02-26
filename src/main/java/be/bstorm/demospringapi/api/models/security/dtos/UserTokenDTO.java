package be.bstorm.demospringapi.api.models.security.dtos;

public record UserTokenDTO(
        UserSessionDTO user,
        String token
) {
}
