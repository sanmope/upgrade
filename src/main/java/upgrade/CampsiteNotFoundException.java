package upgrade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CampsiteNotFoundException extends RuntimeException {

    public CampsiteNotFoundException (String exception){
        super(exception);
    }

}