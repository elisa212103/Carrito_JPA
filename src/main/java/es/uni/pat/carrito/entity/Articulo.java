package es.uni.pat.carrito.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
public class Articulo {

    @Id
    private Long idArticulo;

    @Column(nullable = false)
    @NotBlank
    private String descripcion;

    @Column(nullable = false)
    @Positive
    private double precioUnitario;

    // constructor vacío para JPA
    public Articulo() {}

    public Articulo(Long idArticulo, String descripcion, double precioUnitario) {
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.idArticulo = idArticulo;
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

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}