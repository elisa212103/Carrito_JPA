package es.uni.pat.carrito.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

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
    private int unidades;

    @Column
    @NotNull
    private double precioUnitario;

    @Transient
    public double getPrecioTotal() {
        return unidades * precioUnitario;
    }

    //constructor vacio para JPA
    public Articulo() {}


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


}