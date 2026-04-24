package es.uni.pat.carrito.repositorio;

import es.uni.pat.carrito.entity.Carrito;
import es.uni.pat.carrito.entity.Linea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoLinea extends CrudRepository<Linea, Long> {

    List<Linea> findByCarritoIdCarrito(Long idCarrito);

    Optional<Linea> findByCarritoIdCarritoAndArticuloIdArticulo(Long idCarrito, Long idArticulo);

    Optional<Linea> findByIdLineaAndCarritoIdCarrito(Long idLinea, Long idCarrito);
}
