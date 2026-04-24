package es.uni.pat.carrito.repository;

import es.uni.pat.carrito.entity.Articulo;
import es.uni.pat.carrito.entity.Carrito;
import es.uni.pat.carrito.entity.Linea;
import es.uni.pat.carrito.repositorio.RepoArticulo;
import es.uni.pat.carrito.repositorio.RepoCarrito;
import es.uni.pat.carrito.repositorio.RepoLinea;

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

    @Autowired
    RepoLinea repoLinea;

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

        Articulo articulo = new Articulo();
        articulo.setDescripcion("Libro Java");
        articulo.setPrecioUnitario(10.0);

        // When
        articulo = repoArticulo.save(articulo);

        // Then
        assertNotNull(articulo.getIdArticulo());
    }

    @Test
    void guardarLineaTest() {

        // Given
        Carrito carrito = new Carrito(3L, "elisa.lapastora@gmail.com");
        carrito = repoCarrito.save(carrito);

        Articulo articulo = new Articulo();
        articulo.setDescripcion("Libro Java");
        articulo.setPrecioUnitario(10.0);
        articulo = repoArticulo.save(articulo);

        Linea linea = new Linea();
        linea.setCarrito(carrito);
        linea.setArticulo(articulo);
        linea.setUnidades(3);

        // When
        linea = repoLinea.save(linea);

        // Then
        assertNotNull(linea.getIdLinea());
        assertEquals(carrito.getIdCarrito(), linea.getCarrito().getIdCarrito());
        assertEquals(articulo.getIdArticulo(), linea.getArticulo().getIdArticulo());
        assertEquals(3, linea.getUnidades());
        assertEquals(30.0, linea.getPrecioTotal());
    }

}


