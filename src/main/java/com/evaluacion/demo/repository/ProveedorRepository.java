package com.evaluacion.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evaluacion.demo.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}