package es.uni.pat.carrito.repositorio;

import es.uni.pat.carrito.entity.Articulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//no puedes poner metodos que no se esten usando, da error

@Repository
public interface RepoArticulo extends CrudRepository <Articulo, Long>{
    List<Articulo> findByCarritoIdCarrito(Long idCarrito);

}
