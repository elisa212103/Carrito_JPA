package es.uni.pat.carrito.servicio;

import es.uni.pat.carrito.entity.Articulo;
import es.uni.pat.carrito.entity.Carrito;
import es.uni.pat.carrito.repositorio.RepoArticulo;
import es.uni.pat.carrito.repositorio.RepoCarrito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServicioAcciones {

    @Autowired
    private RepoCarrito repoCarrito;

    @Autowired
    private RepoArticulo repoArticulo;

    private Logger logger = LoggerFactory.getLogger(getClass());

//todo lo que modifique la base
//de datos debería ser transicional
    @Transactional
    public Carrito crearCarrito(Carrito nuevo) {

        Carrito carritoGuardado = repoCarrito.save(nuevo);
        logger.info("Carrito creado con id: " + carritoGuardado.getIdCarrito());

        return carritoGuardado;
    }

    public Carrito obtenerCarrito(Long id) {

        return repoCarrito.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));
    }


    @Transactional
    public Carrito actualizarCarrito(Long id, Carrito actualizado) {

        Carrito existente = repoCarrito.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        actualizado.setIdCarrito(id);
        return repoCarrito.save(actualizado);
    }

    //para borrar excepciones especiales hacer try/catch
    @Transactional(rollbackFor = Exception.class)
    public void borrarCarrito(Long id) throws Exception {

        Carrito carrito = repoCarrito.findById(id).orElse(null);

        if (carrito == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }

        try {
            repoCarrito.delete(carrito);
        } catch (Exception e) {
            throw new Exception("Error al borrar carrito", e);
        }
    }


    @Transactional
    public Articulo añadirLinea(Long idCarrito, Articulo articulo) {

        logger.info("Añadiendo articulo al carrito: " + idCarrito);
        Carrito carrito = repoCarrito.findById(idCarrito)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        articulo.setCarrito(carrito);

        return repoArticulo.save(articulo);
    }

    @Transactional
    public void borrarLinea(Long idArticulo) {

        logger.info("Borrando articulo con id: " + idArticulo);

        Articulo articulo = repoArticulo.findById(idArticulo)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Articulo no encontrado"));

        repoArticulo.delete(articulo);
    }

    //el programa te permite crear un carrito sin articulos
    public double calcularTotal(Long idCarrito) {

        logger.info("Calculando total del carrito: " + idCarrito);

        if (!repoCarrito.existsById(idCarrito)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }

        List<Articulo> articulos = repoArticulo.findByCarritoIdCarrito(idCarrito);

        double total = 0.0;

        for (Articulo a : articulos) {
            total += a.getPrecioTotal();
        }

        return total;
    }
}