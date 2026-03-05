package es.uni.pat.carrito.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    @Column(nullable = false)
    @Positive
    private Long idUsuario;

    @Column(nullable = false)
    private String correoUsuario;

    //mapeo bidireccional para los onetomany
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<Articulo> articulos = new ArrayList<>();

    public Carrito() {}

    // Constructor completo (para tests)
    public Carrito(Long idUsuario, String correoUsuario) {
        this.idUsuario = idUsuario;
        this.correoUsuario = correoUsuario;
    }

    public Long getIdCarrito() { return idCarrito; }
    public void setIdCarrito(Long idCarrito) { this.idCarrito = idCarrito; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public List<Articulo> getArticulos() { return articulos; }

    public double getPrecioFinal() {
        double total = 0.0;
        for (Articulo a : articulos) {
            total += a.getPrecioTotal();
        }
        return total;
    }
}


