package be.bstorm.demospringapi.bll.exceptions.post;

import be.bstorm.demospringapi.bll.exceptions.HolyMotherException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends HolyMotherException {
    public PostNotFoundException(HttpStatus status, String error) {
        super(status, error);
    }
}
