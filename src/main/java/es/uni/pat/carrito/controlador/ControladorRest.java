package es.uni.pat.carrito.controlador;

import es.uni.pat.carrito.dto.UnidadesDTO;
import es.uni.pat.carrito.entity.Articulo;
import es.uni.pat.carrito.entity.Carrito;
import es.uni.pat.carrito.entity.Linea;
import es.uni.pat.carrito.servicio.ServicioAcciones;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/carritos")
public class ControladorRest {

    @Autowired
    private ServicioAcciones servicioAcciones;

    @GetMapping("/publico")
    public String publico() {
        return "ok";
    }


    // CREATE carrito
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito crea(@RequestBody @Valid Carrito nuevo) {
        System.out.println("ENTRA EN CREA");
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

    // Añadir linea al carrito
    @PostMapping("/{idCarrito}/articulos/{idArticulo}")
    @ResponseStatus(HttpStatus.CREATED)
    public Linea add(@PathVariable Long idCarrito,
                        @PathVariable Long idArticulo,
                        @RequestParam int unidades) {
        return servicioAcciones.addLinea(idCarrito, idArticulo, unidades);
    }

    @DeleteMapping("/{idCarrito}/lineas/{idLinea}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarArticulo(@PathVariable Long idCarrito,
                                 @PathVariable Long idLinea) {
        servicioAcciones.borrarLinea(idCarrito, idLinea);
    }

    // Obtener total del carrito
    @GetMapping("/{id}/total")
    public double total(@PathVariable Long id) {

        return servicioAcciones.calcularTotal(id);
    }


    @PatchMapping("/{idCarrito}/lineas/{idLinea}")
    public Linea actualizarUnidades(@PathVariable Long idCarrito,
                                    @PathVariable Long idLinea,
                                    @RequestBody UnidadesDTO dto) {
        return servicioAcciones.actualizarUnidades(idCarrito, idLinea, dto.getUnidades());
    }
}
//ejemplo de PATCH (modificar solo una parte del objeto)
//@PatchMapping("/carritos/{id}")
//public Carrito actualizarCorreo(@PathVariable Long id,
//                                @RequestBody String nuevoCorreo) {
//
//    Carrito carrito = carritoRepository.findById(id).orElseThrow();
//    carrito.setCorreoUsuario(nuevoCorreo);
//
//    return carritoRepository.save(carrito);
//}

//ejemplo de hacerlo con BindingResults (cambiar tambien en service)
//@PostMapping("/api/carritos")
//@ResponseStatus(HttpStatus.CREATED)
//public Carrito crea(@Valid @RequestBody Carrito nuevo,
//                    BindingResult bindingResult) {
//
//    return servicioAcciones.crearCarrito(nuevo, bindingResult);
//}