package es.uni.pat.carrito.repositorio;


import es.uni.pat.carrito.entity.Carrito;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepoCarrito extends CrudRepository<Carrito, Long> {

    // Si quieres buscar por algún campo específico en el futuro
    // Carrito findByNombre(String nombre);

    //CRUDRepository ya te da
    //save()
    //findById()
    //findAll()
    //deleteById()
    //existsById()

}