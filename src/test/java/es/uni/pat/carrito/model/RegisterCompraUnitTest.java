package es.uni.pat.carrito.model;


import es.uni.pat.carrito.entity.Carrito;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterCompraUnitTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidCarrito() {
        // Given ...
        Carrito carrito = new Carrito(345L,"helloworld@");
        // When ...
        Set<ConstraintViolation<Carrito>> violations =
                validator.validate(carrito);
        // Then ...
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNotValidCarrito(){
        // Given ...
        //El valor que pongas en el constructor debe violar exactamente la validación que quieres comprobar
        //en este caso positive
        Carrito carrito = new Carrito(-1L,"helloworld@");
        // When ...
        Set<ConstraintViolation<Carrito>> violations =
                validator.validate(carrito);
        // Then ...
        assertEquals(1, violations.size());
        List<ConstraintViolation<Carrito>> errores = new ArrayList<>(violations);
        assertEquals("idUsuario", errores.get(0).getPropertyPath().toString());
        assertEquals(-1L, errores.get(0).getInvalidValue());
    }

}
