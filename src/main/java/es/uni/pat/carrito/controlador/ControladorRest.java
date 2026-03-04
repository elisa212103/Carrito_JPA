package es.uni.pat.carrito.controlador;

import es.uni.pat.carrito.entity.Articulo;
import es.uni.pat.carrito.entity.Carrito;
import es.uni.pat.carrito.servicio.ServicioAcciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carritos")
public class ControladorRest {

    @Autowired
    private ServicioAcciones servicioAcciones;

    // CREATE carrito
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito crea(@RequestBody Carrito nuevo) {
        return servicioAcciones.crearCarrito(nuevo);
    }

    // READ carrito por id
    @GetMapping("/{id}")
    public Carrito leer(@PathVariable Long id) {
        return servicioAcciones.obtenerCarrito(id);
    }

    // UPDATE carrito
    @PutMapping("/{id}")
    public Carrito actualizar(@PathVariable Long id,
                                     @RequestBody Carrito actualizado) {
        return servicioAcciones.actualizarCarrito(id, actualizado);
    }

    //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@PathVariable Long id) {
        try {
            servicioAcciones.borrarCarrito(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/articulos/{idArticulo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarLinea(@PathVariable Long idArticulo) {
        servicioAcciones.borrarLinea(idArticulo);
    }

    // Añadir artículo al carrito
    @PostMapping("/{id}/articulos")
    @ResponseStatus(HttpStatus.CREATED)
    public Articulo añadir(@PathVariable Long id,
                                   @RequestBody Articulo articulo) {
        return servicioAcciones.añadirLinea(id, articulo);
    }

    // Obtener total del carrito
    @GetMapping("/{id}/total")
    public double total(@PathVariable Long id) {
        return servicioAcciones.calcularTotal(id);
    }
}