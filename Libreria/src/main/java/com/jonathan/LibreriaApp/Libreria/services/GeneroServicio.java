package com.jonathan.LibreriaApp.Libreria.services;

import com.jonathan.LibreriaApp.Libreria.DTOs.GeneroDTO;
import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDetalleDTO;
import com.jonathan.LibreriaApp.Libreria.entities.Genero;
import com.jonathan.LibreriaApp.Libreria.repositories.GeneroRepositorio;
import com.jonathan.LibreriaApp.Libreria.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeneroServicio {
    @Autowired
    private GeneroRepositorio generoRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    private GeneroDTO convertirAGeneroDTO(Genero genero) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(genero.getId());
        dto.setNombre(genero.getNombre());
        return dto;
    }


    // Obtener todos los géneros
    public List<GeneroDTO> obtenerTodosLosGeneros() {
        return generoRepositorio.findAll()
                .stream()
                .map(this::convertirAGeneroDTO)
                .collect(Collectors.toList());
    }

    // Obtener un género por ID
    public Optional<GeneroDTO> obtenerGeneroPorId(Long id) {
        return generoRepositorio.findById(id).map(this::convertirAGeneroDTO);
    }

    // Crear un nuevo género
    public GeneroDTO crearGenero(GeneroDTO generoDTO) {
        Genero genero = new Genero();
        genero.setNombre(generoDTO.getNombre());
        Genero nuevoGenero = generoRepositorio.save(genero);
        return convertirAGeneroDTO(nuevoGenero);
    }

    // Actualizar un género existente
    public Optional<GeneroDTO> actualizarGenero(Long id, GeneroDTO generoDTO) {
        return generoRepositorio.findById(id).map(genero -> {
            genero.setNombre(generoDTO.getNombre());
            Genero generoActualizado = generoRepositorio.save(genero);
            return convertirAGeneroDTO(generoActualizado);
        });
    }

    //  Eliminar un género
    public boolean eliminarGenero(Long id) {
        if (generoRepositorio.existsById(id)) {
            generoRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    //  Obtener todos los libros asociados a un género
    public List<LibroDetalleDTO> obtenerLibrosPorGenero(Long generoId) {
        return libroRepositorio.findByGeneros_Id(generoId)
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
}
