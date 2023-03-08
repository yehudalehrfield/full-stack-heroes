package yehuda.heroes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HeroesNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(HeroesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String HeroesNotFoundHandler(HeroesNotFoundException ex){ return ex.getMessage();}

}
