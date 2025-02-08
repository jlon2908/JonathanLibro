package com.jonathan.LibreriaApp.Libreria.controller;

import com.jonathan.LibreriaApp.Libreria.DTOs.EditorialDTO;
import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDetalleDTO;
import com.jonathan.LibreriaApp.Libreria.services.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/editoriales")
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialServicio;

    //  Obtener todas las editoriales
    @GetMapping
    public ResponseEntity<List<EditorialDTO>> obtenerTodasLasEditoriales() {
        List<EditorialDTO> editoriales = editorialServicio.obtenerTodasLasEditoriales();
        return ResponseEntity.ok(editoriales);
    }

    // Obtener una editorial por ID
    @GetMapping("/{id}")
    public ResponseEntity<EditorialDTO> obtenerEditorialPorId(@PathVariable Long id) {
        Optional<EditorialDTO> editorial = editorialServicio.obtenerEditorialPorId(id);
        return editorial.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //  Crear una nueva editorial
    @PostMapping
    public ResponseEntity<EditorialDTO> crearEditorial(@RequestBody EditorialDTO editorialDTO) {
        EditorialDTO nuevaEditorial = editorialServicio.crearEditorial(editorialDTO);
        return ResponseEntity.ok(nuevaEditorial);
    }

    // Actualizar una editorial existente
    @PutMapping("/{id}")
    public ResponseEntity<EditorialDTO> actualizarEditorial(@PathVariable Long id, @RequestBody EditorialDTO editorialDTO) {
        Optional<EditorialDTO> editorialActualizada = editorialServicio.actualizarEditorial(id, editorialDTO);
        return editorialActualizada.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una editorial
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEditorial(@PathVariable Long id) {
        if (editorialServicio.eliminarEditorial(id)) {
            return ResponseEntity.ok("Editorial eliminada correctamente.");
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Obtener todos los libros de una editorial
    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroDetalleDTO>> obtenerLibrosPorEditorial(@PathVariable Long id) {
        List<LibroDetalleDTO> libros = editorialServicio.obtenerLibrosPorEditorial(id);
        return ResponseEntity.ok(libros);
    }

}
