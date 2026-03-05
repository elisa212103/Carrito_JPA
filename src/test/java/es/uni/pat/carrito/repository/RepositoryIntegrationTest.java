package es.uni.pat.carrito.repository;

import es.uni.pat.carrito.entity.Articulo;
import es.uni.pat.carrito.entity.Carrito;
import es.uni.pat.carrito.repositorio.RepoArticulo;
import es.uni.pat.carrito.repositorio.RepoCarrito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

//CAMBIAR application.properties

@DataJpaTest
public class RepositoryIntegrationTest {

    @Autowired
    RepoArticulo repoArticulo;

    @Autowired
    RepoCarrito repoCarrito;

    @Test
    void guardarCarritoTest() {
        //Dado
        Carrito carrito=new Carrito(3L,"elisa.lapastora@gmail");
        //Cuándo
        carrito=repoCarrito.save(carrito);
        //Entonces
        Assertions.assertNotNull(repoCarrito.findById(carrito.getIdCarrito()));
        assertTrue(repoCarrito.findById(carrito.getIdCarrito()).isPresent());
    }

    @Test
    void guardarArticuloTest() {

        // Given
        Carrito carrito = new Carrito(3L, "elisa.lapastora@gmail");
        carrito = repoCarrito.save(carrito);

        Articulo articulo = new Articulo();
        articulo.setDescripcion("Libro Java");
        articulo.setUnidades(2);
        articulo.setPrecioUnitario(10.0);
        articulo.setCarrito(carrito);

        // When
        articulo = repoArticulo.save(articulo);

        // Then
        assertNotNull(articulo.getIdArticulo());
        assertEquals(carrito.getIdCarrito(), articulo.getCarrito().getIdCarrito());
    }

}

