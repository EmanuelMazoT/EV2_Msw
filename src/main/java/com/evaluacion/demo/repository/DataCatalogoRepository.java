package com.evaluacion.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evaluacion.demo.model.DataCatalogo;

public interface DataCatalogoRepository extends JpaRepository<DataCatalogo, Integer> {
}
