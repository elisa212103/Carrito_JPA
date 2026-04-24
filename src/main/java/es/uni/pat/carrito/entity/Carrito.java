package es.uni.pat.carrito.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Email
    @NotBlank
    private String correoUsuario;

    //mapeo bidireccional para los onetomany
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Linea> lineas = new ArrayList<>();

    //DIFERENCIA -> CascadeType, si se borra carrito se borran sus lineas (padre-hijo)
    //           -> OrphanRemoval, si se borra una linea de carrito se borra de BD lineas

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


    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
    }
    public List<Linea> getLineas() { return lineas; }

    //para los datos calculados, que no queremos almacenar
    @Transient
    public double getPrecioFinal() {
        double total = 0.0;
        for (Linea l : lineas) {
            total += l.getPrecioTotal();
        }
        return total;
    }
}


