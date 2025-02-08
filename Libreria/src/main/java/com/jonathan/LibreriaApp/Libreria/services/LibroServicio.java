package com.jonathan.LibreriaApp.Libreria.services;

import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDTO;
import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDetalleDTO;
import com.jonathan.LibreriaApp.Libreria.entities.Autor;
import com.jonathan.LibreriaApp.Libreria.entities.Editorial;
import com.jonathan.LibreriaApp.Libreria.entities.Genero;
import com.jonathan.LibreriaApp.Libreria.entities.Libro;
import com.jonathan.LibreriaApp.Libreria.repositories.AutorRepositorio;
import com.jonathan.LibreriaApp.Libreria.repositories.EditorialRepositorio;
import com.jonathan.LibreriaApp.Libreria.repositories.GeneroRepositorio;
import com.jonathan.LibreriaApp.Libreria.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private GeneroRepositorio generoRepositorio;

    //Get todos los libros
    public List<LibroDetalleDTO> obtenerTodosLosLibros() {
        List<Libro> libros = libroRepositorio.findAll();
        return libros.stream().map(this::convertirALibroDetalleDTO).collect(Collectors.toList());
    }

    //  Obtener un libro por ID
    public Optional<LibroDetalleDTO> obtenerLibroPorId(Long id) {
        return libroRepositorio.findById(id).map(this::convertirALibroDetalleDTO);
    }

    // Crear un libro
    public Optional<LibroDetalleDTO> crearLibro(LibroDTO libroDTO) {
        Optional<Autor> autor = autorRepositorio.findById(libroDTO.getAutorId());
        Optional<Editorial> editorial = editorialRepositorio.findById(libroDTO.getEditorialId());

        if (autor.isEmpty() || editorial.isEmpty()) {
            return Optional.empty(); // No se puede crear el libro sin autor o editorial válidos
        }

        Libro libro = new Libro();
        libro.setTitulo(libroDTO.getTitulo());
        libro.setDescripcion(libroDTO.getDescripcion());
        libro.setAutor(autor.get());
        libro.setEditorial(editorial.get());

        Libro nuevoLibro = libroRepositorio.save(libro);
        return Optional.of(convertirALibroDetalleDTO(nuevoLibro));
    }

    // Actualizar un libro
    public Optional<LibroDetalleDTO> actualizarLibro(Long id, LibroDTO libroDTO) {
        return libroRepositorio.findById(id).map(libro -> {
            Optional<Autor> autor = autorRepositorio.findById(libroDTO.getAutorId());
            Optional<Editorial> editorial = editorialRepositorio.findById(libroDTO.getEditorialId());

            if (autor.isEmpty() || editorial.isEmpty()) {
                return null; // Si autor o editorial no existen, no se actualiza
            }

            libro.setTitulo(libroDTO.getTitulo());
            libro.setDescripcion(libroDTO.getDescripcion());
            libro.setAutor(autor.get());
            libro.setEditorial(editorial.get());

            return convertirALibroDetalleDTO(libroRepositorio.save(libro));
        });
    }
    //  Eliminar un libro
    public boolean eliminarLibro(Long id) {
        if (libroRepositorio.existsById(id)) {
            libroRepositorio.deleteById(id);
            return true;
        }
        return false;
    }
    public List<LibroDetalleDTO> buscarLibrosPorAutor(Long autorId) {
        List<Libro> libros = libroRepositorio.findByAutorId(autorId);
        return libros.stream().map(this::convertirALibroDetalleDTO).collect(Collectors.toList());
    }

    public List<LibroDetalleDTO> buscarLibrosPorEditorial(Long editorialId) {
        List<Libro> libros = libroRepositorio.findByEditorialId(editorialId);
        return libros.stream().map(this::convertirALibroDetalleDTO).collect(Collectors.toList());
    }

    public List<LibroDetalleDTO> buscarLibrosPorTitulo(String termino) {
        List<Libro> libros = libroRepositorio.searchByTitulo(termino);
        return libros.stream().map(this::convertirALibroDetalleDTO).collect(Collectors.toList());
    }

    public Optional<LibroDetalleDTO> asignarGenero(Long libroId, Long generoId) {
        Optional<Libro> libroOpt = libroRepositorio.findById(libroId);
        Optional<Genero> generoOpt = generoRepositorio.findById(generoId);

        if (libroOpt.isPresent() && generoOpt.isPresent()) {
            Libro libro = libroOpt.get();
            Genero genero = generoOpt.get();

            if (!libro.getGeneros().contains(genero)) {
                libro.getGeneros().add(genero);
                libroRepositorio.save(libro);
            }

            return Optional.of(convertirALibroDetalleDTO(libro));
        }
        return Optional.empty();
    }

    public Optional<LibroDetalleDTO> eliminarGenero(Long libroId, Long generoId) {
        Optional<Libro> libroOpt = libroRepositorio.findById(libroId);
        Optional<Genero> generoOpt = generoRepositorio.findById(generoId);

        if (libroOpt.isPresent() && generoOpt.isPresent()) {
            Libro libro = libroOpt.get();
            Genero genero = generoOpt.get();

            libro.getGeneros().remove(genero);
            libroRepositorio.save(libro);

            return Optional.of(convertirALibroDetalleDTO(libro));
        }
        return Optional.empty();
    }

    private LibroDetalleDTO convertirALibroDetalleDTO(Libro libro) {
        LibroDetalleDTO dto = new LibroDetalleDTO();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setDescripcion(libro.getDescripcion());
        dto.setAutorNombre(libro.getAutor().getNombre());
        dto.setEditorialNombre(libro.getEditorial().getNombre());
        dto.setGeneros(libro.getGeneros().stream().map(Genero::getNombre).collect(Collectors.toList()));
        dto.setCreatedAt(libro.getCreatedAt());
        dto.setUpdatedAt(libro.getUpdatedAt());
        return dto;
    }
}
