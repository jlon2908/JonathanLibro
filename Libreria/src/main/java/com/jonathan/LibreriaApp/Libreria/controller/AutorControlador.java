package com.jonathan.LibreriaApp.Libreria.controller;

import com.jonathan.LibreriaApp.Libreria.DTOs.AutorDTO;
import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDetalleDTO;
import com.jonathan.LibreriaApp.Libreria.services.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autores")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    // Obtener todos los autores
    @GetMapping
    public ResponseEntity<List<AutorDTO>> obtenerTodosLosAutores() {
        List<AutorDTO> autores = autorServicio.obtenerTodosLosAutores();
        return ResponseEntity.ok(autores);
    }

    // Obtener un autor por ID
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obtenerAutorPorId(@PathVariable Long id) {
        Optional<AutorDTO> autor = autorServicio.obtenerAutorPorId(id);
        return autor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    //  Crear un nuevo autor
    @PostMapping
    public ResponseEntity<AutorDTO> crearAutor(@RequestBody AutorDTO autorDTO) {
        AutorDTO nuevoAutor = autorServicio.crearAutor(autorDTO);
        return ResponseEntity.ok(nuevoAutor);
    }


    // Actualizar un autor existente
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> actualizarAutor(@PathVariable Long id, @RequestBody AutorDTO autorDTO) {
        Optional<AutorDTO> autorActualizado = autorServicio.actualizarAutor(id, autorDTO);
        return autorActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    // ✅ Eliminar un autor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAutor(@PathVariable Long id) {
        if (autorServicio.eliminarAutor(id)) {
            return ResponseEntity.ok("Autor eliminado correctamente.");
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Obtener todos los libros de un autor
    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroDetalleDTO>> obtenerLibrosPorAutor(@PathVariable Long id) {
        List<LibroDetalleDTO> libros = autorServicio.obtenerLibrosPorAutor(id);
        return ResponseEntity.ok(libros);
    }

}
