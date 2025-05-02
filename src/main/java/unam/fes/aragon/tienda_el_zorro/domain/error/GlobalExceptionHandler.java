package unam.fes.aragon.tienda_el_zorro.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DinError.class)
    public ResponseEntity<ErrorNegocio> handleDinError(DinError ex) {
        ErrorNegocio error = ex.getError();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // Usa 409 para conflictos
    }
}
