package be.bstorm.demospringapi.bll.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString @EqualsAndHashCode(callSuper = false)
public abstract class HolyMotherException extends RuntimeException{

    private final HttpStatus status;
    private final Object error;

    public HolyMotherException(HttpStatus status, Object error) {
        this.status = status;
        this.error = error;
    }
}
