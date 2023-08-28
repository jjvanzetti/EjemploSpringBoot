package com.soluciones.Facturacion.domain.gestion.producto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Data;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Debes especificar el nombre")
    @Size(min = 1, max = 50, message = "El nombre debe medir entre 1 y 50")
    private String nombre;

//    @NotNull(message = "Debes especificar el código")
    @Size(min = 1, max = 50, message = "El código debe medir entre 1 y 50")
    private String codigo;

    @NotNull(message = "Debes especificar el precio")
    @Min(value = 0, message = "El precio mínimo es 0")
    private Float precio;

    @NotNull(message = "Debes especificar la existencia")
    @Min(value = 0, message = "La existencia mínima es 0")
    private Float existencia;

    private int estado;

    public Producto(String nombre, String codigo, Float precio, Float existencia, Integer id) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.existencia = existencia;
        this.id = id;
        this.estado = 0;
    }

    public Producto(String nombre, String codigo, Float precio, Float existencia) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.existencia = existencia;
    }

    public Producto(String codigo) {
        this.codigo = codigo;
    }

    public Producto() {
    }

    public boolean sinExistencia() {
        return this.existencia <= 0;
    }

    public void restarExistencia(Float existencia) {
        this.existencia -= existencia;
    }

}