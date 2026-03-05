package es.uni.pat.carrito.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Entity
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticulo;

    @ManyToOne
    @JoinColumn(name = "carrito_id", referencedColumnName = "idCarrito", nullable = false)
    private Carrito carrito;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    @Min(1)
    private int unidades;

    @Column
    @NotNull
    @Positive
    private double precioUnitario;

    @Transient
    public double getPrecioTotal() {
        return unidades * precioUnitario;
    }

    //constructor vacio para JPA
    public Articulo() {}

    public Articulo(String descripcion, int unidades, double precioUnitario, Carrito carrito) {
        this.descripcion = descripcion;
        this.unidades = unidades;
        this.precioUnitario = precioUnitario;
        this.carrito = carrito;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setCarrito(Carrito carrito){
        this.carrito = carrito;
    }

    public Carrito getCarrito(){
        return carrito;
    }


}

//    mas validaciones:
//   - si quiero que sea o una u otra:
//     @Pattern(regexp = "^(compra|venta)$", message = "El valor debe ser 'compra' o 'venta'")
//   - comprobar que es email (@)
//     @Email