package es.uni.pat.carrito.repositorio;

import es.uni.pat.carrito.entity.Articulo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//no puedes poner metodos que no se esten usando, da error

@Repository
public interface RepoArticulo extends CrudRepository <Articulo, Long>{
    //
}

