package es.uni.pat.carrito.servicio;

import es.uni.pat.carrito.entity.Articulo;
import es.uni.pat.carrito.entity.Carrito;
import es.uni.pat.carrito.entity.Linea;
import es.uni.pat.carrito.repositorio.RepoCarrito;
import es.uni.pat.carrito.repositorio.RepoArticulo;
import es.uni.pat.carrito.repositorio.RepoLinea;
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

    @Autowired
    private RepoLinea repoLinea;

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
    public Linea addLinea(Long idCarrito, Long idArticulo, int unidades) {
        logger.info("Añadiendo artículo {} al carrito {}", idArticulo, idCarrito);

        if (unidades < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las unidades deben ser al menos 1");
        }

        Carrito carrito = repoCarrito.findById(idCarrito)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        Articulo articulo = repoArticulo.findById(idArticulo)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Artículo no encontrado"));

        Linea lineaExistente = repoLinea
                .findByCarritoIdCarritoAndArticuloIdArticulo(idCarrito, idArticulo)
                .orElse(null);

        if (lineaExistente != null) {
            lineaExistente.setUnidades(lineaExistente.getUnidades() + unidades);
            return repoLinea.save(lineaExistente);
        } else {
            Linea nuevaLinea = new Linea();
            nuevaLinea.setCarrito(carrito);
            nuevaLinea.setArticulo(articulo);
            nuevaLinea.setUnidades(unidades);
            return repoLinea.save(nuevaLinea);
        }
    }


    @Transactional
    public void borrarLinea(Long idCarrito, Long idLinea) {
        logger.info("Borrando línea {} del carrito {}", idLinea, idCarrito);

        Linea lineaExistente = repoLinea.findByIdLineaAndCarritoIdCarrito(idLinea, idCarrito)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada en el carrito"));

        repoLinea.delete(lineaExistente);
    }


    @Transactional
    public Linea actualizarUnidades(Long idCarrito, Long idLinea, int nuevasUnidades) {
        logger.info("Actualizando unidades de la línea {} del carrito {}", idLinea, idCarrito);

        Linea linea = repoLinea.findByIdLineaAndCarritoIdCarrito(idLinea, idCarrito)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada en el carrito"));

        if (nuevasUnidades < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las unidades no pueden ser negativas");
        }

        if (nuevasUnidades == 0) {
            repoLinea.delete(linea);
            return linea;
        }

        linea.setUnidades(nuevasUnidades);
        return repoLinea.save(linea);
    }

    public double calcularTotal(Long idCarrito) {
        logger.info("Calculando total del carrito {}", idCarrito);

        if (!repoCarrito.existsById(idCarrito)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }

        List<Linea> lineas = repoLinea.findByCarritoIdCarrito(idCarrito);

        double total = 0.0;
        for (Linea l : lineas) {
            total += l.getPrecioTotal();
        }

        return total;
    }
}

//ejemplo para hacerlo con bindingResults
//@Transactional
//public Carrito crearCarrito(Carrito nuevo, BindingResult bindingResult) {
//
//    if (bindingResult.hasErrors()) {
//        throw new ExcepcionCarritoIncorrecto(bindingResult);
//    }
//
//    return repoCarrito.save(nuevo);
//}