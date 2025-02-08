package com.jonathan.LibreriaApp.Libreria.controller;

import com.jonathan.LibreriaApp.Libreria.DTOs.GeneroDTO;
import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDetalleDTO;
import com.jonathan.LibreriaApp.Libreria.services.GeneroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/generos")
public class GeneroControlador {
    @Autowired
    private GeneroServicio generoServicio;

    //  Obtener todos los géneros
    @GetMapping
    public ResponseEntity<List<GeneroDTO>> obtenerTodosLosGeneros() {
        List<GeneroDTO> generos = generoServicio.obtenerTodosLosGeneros();
        return ResponseEntity.ok(generos);
    }


    // Obtener un género por ID
    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> obtenerGeneroPorId(@PathVariable Long id) {
        Optional<GeneroDTO> genero = generoServicio.obtenerGeneroPorId(id);
        return genero.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo género
    @PostMapping
    public ResponseEntity<GeneroDTO> crearGenero(@RequestBody GeneroDTO generoDTO) {
        GeneroDTO nuevoGenero = generoServicio.crearGenero(generoDTO);
        return ResponseEntity.ok(nuevoGenero);
    }

    //  Actualizar un género existente
    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> actualizarGenero(@PathVariable Long id, @RequestBody GeneroDTO generoDTO) {
        Optional<GeneroDTO> generoActualizado = generoServicio.actualizarGenero(id, generoDTO);
        return generoActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //  Eliminar un género
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGenero(@PathVariable Long id) {
        if (generoServicio.eliminarGenero(id)) {
            return ResponseEntity.ok("Género eliminado correctamente.");
        }
        return ResponseEntity.notFound().build();
    }

    // Obtener todos los libros asociados a un género
    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroDetalleDTO>> obtenerLibrosPorGenero(@PathVariable Long id) {
        List<LibroDetalleDTO> libros = generoServicio.obtenerLibrosPorGenero(id);
        return ResponseEntity.ok(libros);
    }
}
