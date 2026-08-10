package com.evaluacion.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "data_catalogo")
public class DataCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddatacatalogo")
    private Integer idDataCatalogo;

    @Column(name = "descripcion", nullable = false, unique = true)
    private String descripcion;

    public DataCatalogo() {
    }

    public Integer getIdDataCatalogo() {
        return idDataCatalogo;
    }

    public void setIdDataCatalogo(Integer idDataCatalogo) {
        this.idDataCatalogo = idDataCatalogo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
