package com.evaluacion.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacion.demo.model.DataCatalogo;
import com.evaluacion.demo.model.Pais;
import com.evaluacion.demo.model.Proveedor;
import com.evaluacion.demo.repository.DataCatalogoRepository;
import com.evaluacion.demo.repository.PaisRepository;
import com.evaluacion.demo.repository.ProveedorRepository;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorRepository proveedorRepository;
    private final DataCatalogoRepository dataCatalogoRepository;
    private final PaisRepository paisRepository;

    public ProveedorController(
            ProveedorRepository proveedorRepository,
            DataCatalogoRepository dataCatalogoRepository,
            PaisRepository paisRepository) {

        this.proveedorRepository = proveedorRepository;
        this.dataCatalogoRepository = dataCatalogoRepository;
        this.paisRepository = paisRepository;
    }

    // GET - LISTAR TODOS
    @GetMapping
    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    // GET - BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(
            @PathVariable Integer id) {

        return proveedorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - CREAR
    @PostMapping
    public ResponseEntity<?> crearProveedor(
            @RequestBody Proveedor proveedor) {

        if (proveedor.getTipoProveedor() == null
                || proveedor.getTipoProveedor().getIdDataCatalogo() == null
                || proveedor.getPais() == null
                || proveedor.getPais().getIdPais() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "Debe indicar tipoProveedor.idDataCatalogo y pais.idPais"
                    ));
        }

        DataCatalogo tipoProveedor = dataCatalogoRepository
                .findById(
                        proveedor.getTipoProveedor()
                                .getIdDataCatalogo()
                )
                .orElse(null);

        if (tipoProveedor == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "El tipo de proveedor no existe"
                    ));
        }

        Pais pais = paisRepository
                .findById(
                        proveedor.getPais()
                                .getIdPais()
                )
                .orElse(null);

        if (pais == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "El pais no existe"
                    ));
        }

        proveedor.setIdProveedor(null);
        proveedor.setTipoProveedor(tipoProveedor);
        proveedor.setPais(pais);

        proveedor.setFechaRegistro(LocalDateTime.now());
        proveedor.setFechaActualizacion(LocalDateTime.now());

        if (proveedor.getEstado() == null) {
            proveedor.setEstado(1);
        }

        try {

            Proveedor guardado =
                    proveedorRepository.save(proveedor);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(guardado);

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error",
                            "No se pudo crear el proveedor. Verifique que el DNI no este repetido."
                    ));
        }
    }

    // PUT - ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProveedor(
            @PathVariable Integer id,
            @RequestBody Proveedor datos) {

        Proveedor proveedor =
                proveedorRepository.findById(id)
                        .orElse(null);

        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }

        if (datos.getTipoProveedor() == null
                || datos.getTipoProveedor().getIdDataCatalogo() == null
                || datos.getPais() == null
                || datos.getPais().getIdPais() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "Debe indicar tipoProveedor.idDataCatalogo y pais.idPais"
                    ));
        }

        DataCatalogo tipoProveedor =
                dataCatalogoRepository
                        .findById(
                                datos.getTipoProveedor()
                                        .getIdDataCatalogo()
                        )
                        .orElse(null);

        if (tipoProveedor == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "El tipo de proveedor no existe"
                    ));
        }

        Pais pais =
                paisRepository
                        .findById(
                                datos.getPais()
                                        .getIdPais()
                        )
                        .orElse(null);

        if (pais == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "El pais no existe"
                    ));
        }

        proveedor.setNombre(datos.getNombre());
        proveedor.setDni(datos.getDni());
        proveedor.setTipoProveedor(tipoProveedor);
        proveedor.setPais(pais);
        proveedor.setEstado(datos.getEstado());

        proveedor.setFechaActualizacion(
                LocalDateTime.now()
        );

        try {

            Proveedor actualizado =
                    proveedorRepository.save(proveedor);

            return ResponseEntity.ok(actualizado);

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error",
                            "No se pudo actualizar el proveedor. Verifique que el DNI no este repetido."
                    ));
        }
    }

    // DELETE - ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(
            @PathVariable Integer id) {

        if (!proveedorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        proveedorRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}