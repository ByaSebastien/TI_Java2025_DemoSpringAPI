package be.bstorm.demospringapi.bll.exceptions.user;

import be.bstorm.demospringapi.bll.exceptions.HolyMotherException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends HolyMotherException {

    public UserAlreadyExistException(HttpStatus status, String error) {
        super(status, error);
    }
}
