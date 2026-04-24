package es.uni.pat.carrito.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
public class Linea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLinea;

    @ManyToOne
    @JsonIgnore  //hay que tener cuidado al meter objetos dentro de objetos, bucle infinito en la respuesta al JSON
    @JoinColumn(name = "carrito_id", referencedColumnName = "idCarrito", nullable = false)
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "articulo_id", referencedColumnName = "idArticulo", nullable = false)
    private Articulo articulo;

    @Column(nullable = false)
    @Min(1)
    private int unidades;

    public Linea() {}

    public Linea(Carrito carrito, Articulo articulo, int unidades) {
        this.carrito = carrito;
        this.articulo = articulo;
        this.unidades = unidades;
    }

    @Transient
    public double getPrecioTotal() {
        return unidades * articulo.getPrecioUnitario();
    }

    public Long getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Long idLinea) {
        this.idLinea = idLinea;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
}