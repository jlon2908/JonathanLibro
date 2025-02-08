package com.jonathan.LibreriaApp.Libreria.controller;

import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDTO;
import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDetalleDTO;
import com.jonathan.LibreriaApp.Libreria.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;


    //  Obtener todos los libros
    @GetMapping
    public ResponseEntity<List<LibroDetalleDTO>> obtenerTodosLosLibros() {
        List<LibroDetalleDTO> libros = libroServicio.obtenerTodosLosLibros();
        return ResponseEntity.ok(libros);
    }
    // Obtener un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<LibroDetalleDTO> obtenerLibroPorId(@PathVariable Long id) {
        Optional<LibroDetalleDTO> libro = libroServicio.obtenerLibroPorId(id);
        return libro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    //  Crear un libro
    @PostMapping
    public ResponseEntity<LibroDetalleDTO> crearLibro(@RequestBody LibroDTO libroDTO) {
        Optional<LibroDetalleDTO> nuevoLibro = libroServicio.crearLibro(libroDTO);
        return nuevoLibro.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    //  Actualizar un libro
    @PutMapping("/{id}")
    public ResponseEntity<LibroDetalleDTO> actualizarLibro(@PathVariable Long id, @RequestBody LibroDTO libroDTO) {
        Optional<LibroDetalleDTO> libroActualizado = libroServicio.actualizarLibro(id, libroDTO);
        return libroActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLibro(@PathVariable Long id) {
        if (libroServicio.eliminarLibro(id)) {
            return ResponseEntity.ok("Libro eliminado correctamente.");
        }
        return ResponseEntity.notFound().build();
    }

    // Obtener libros por autor
    @GetMapping("/autores/{autorId}")
    public ResponseEntity<List<LibroDetalleDTO>> obtenerLibrosPorAutor(@PathVariable Long autorId) {
        List<LibroDetalleDTO> libros = libroServicio.buscarLibrosPorAutor(autorId);
        return ResponseEntity.ok(libros);
    }

    // Obtener libros por editorial
    @GetMapping("/editoriales/{editorialId}")
    public ResponseEntity<List<LibroDetalleDTO>> obtenerLibrosPorEditorial(@PathVariable Long editorialId) {
        List<LibroDetalleDTO> libros = libroServicio.buscarLibrosPorEditorial(editorialId);
        return ResponseEntity.ok(libros);
    }

    //  Asignar género a un libro
    @PostMapping("/{libroId}/generos/{generoId}")
    public ResponseEntity<LibroDetalleDTO> asignarGenero(@PathVariable Long libroId, @PathVariable Long generoId) {
        Optional<LibroDetalleDTO> libro = libroServicio.asignarGenero(libroId, generoId);
        return libro.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }


    //  Eliminar género de un libro
    @DeleteMapping("/{libroId}/generos/{generoId}")
    public ResponseEntity<LibroDetalleDTO> eliminarGenero(@PathVariable Long libroId, @PathVariable Long generoId) {
        Optional<LibroDetalleDTO> libro = libroServicio.eliminarGenero(libroId, generoId);
        return libro.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    //  Buscar libros por término
    @GetMapping("/search/{termino}")
    public ResponseEntity<List<LibroDetalleDTO>> buscarLibrosPorTermino(@PathVariable String termino) {
        List<LibroDetalleDTO> libros = libroServicio.buscarLibrosPorTitulo(termino);
        return ResponseEntity.ok(libros);
    }

}
