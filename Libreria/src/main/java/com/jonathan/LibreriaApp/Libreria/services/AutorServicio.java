package com.jonathan.LibreriaApp.Libreria.services;

import com.jonathan.LibreriaApp.Libreria.DTOs.AutorDTO;
import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDetalleDTO;
import com.jonathan.LibreriaApp.Libreria.entities.Autor;
import com.jonathan.LibreriaApp.Libreria.repositories.AutorRepositorio;
import com.jonathan.LibreriaApp.Libreria.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    //  Obtener todos los autores
    public List<AutorDTO> obtenerTodosLosAutores() {
        return autorRepositorio.findAll()
                .stream()
                .map(this::convertirAAutorDTO)
                .collect(Collectors.toList());
    }

    //  Obtener un autor por ID
    public Optional<AutorDTO> obtenerAutorPorId(Long id) {
        return autorRepositorio.findById(id).map(this::convertirAAutorDTO);
    }

    //  Crear un nuevo autor
    public AutorDTO crearAutor(AutorDTO autorDTO) {
        Autor autor = new Autor();
        autor.setNombre(autorDTO.getNombre());
        autor.setNacionalidad(autorDTO.getNacionalidad());
        autor.setEdad(autorDTO.getEdad());
        Autor nuevoAutor = autorRepositorio.save(autor);
        return convertirAAutorDTO(nuevoAutor);
    }
    //  Eliminar un autor
    public boolean eliminarAutor(Long id) {
        if (autorRepositorio.existsById(id)) {
            autorRepositorio.deleteById(id);
            return true;
        }
        return false;
    }


    //  Obtener todos los libros de un autor
    public List<LibroDetalleDTO> obtenerLibrosPorAutor(Long autorId) {
        return libroRepositorio.findByAutorId(autorId)
                .stream()
                .map(libro -> new LibroDetalleDTO(
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getDescripcion(),
                        libro.getAutor().getNombre(),
                        libro.getEditorial().getNombre(),
                        libro.getGeneros().stream().map(g -> g.getNombre()).collect(Collectors.toList()),
                        libro.getCreatedAt(),
                        libro.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    // Actualizar un autor existente
    public Optional<AutorDTO> actualizarAutor(Long id, AutorDTO autorDTO) {
        return autorRepositorio.findById(id).map(autor -> {
            autor.setNombre(autorDTO.getNombre());
            autor.setNacionalidad(autorDTO.getNacionalidad());
            autor.setEdad(autorDTO.getEdad());
            Autor autorActualizado = autorRepositorio.save(autor);
            return convertirAAutorDTO(autorActualizado);
        });
    }

    // Conversión de entidad a DTO
    private AutorDTO convertirAAutorDTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setNacionalidad(autor.getNacionalidad());
        dto.setEdad(autor.getEdad());
        return dto;
    }
}
