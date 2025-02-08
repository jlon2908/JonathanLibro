package com.jonathan.LibreriaApp.Libreria.services;

import com.jonathan.LibreriaApp.Libreria.DTOs.EditorialDTO;
import com.jonathan.LibreriaApp.Libreria.DTOs.LibroDetalleDTO;
import com.jonathan.LibreriaApp.Libreria.entities.Editorial;
import com.jonathan.LibreriaApp.Libreria.repositories.EditorialRepositorio;
import com.jonathan.LibreriaApp.Libreria.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    private EditorialDTO convertirAEditorialDTO(Editorial editorial) {
        EditorialDTO dto = new EditorialDTO();
        dto.setId(editorial.getId());
        dto.setNombre(editorial.getNombre());
        dto.setUbicacion(editorial.getUbicacion());
        return dto;
    }
    // Obtener todas las editoriales
    public List<EditorialDTO> obtenerTodasLasEditoriales() {
        return editorialRepositorio.findAll()
                .stream()
                .map(this::convertirAEditorialDTO)
                .collect(Collectors.toList());
    }


    //  Obtener una editorial por ID
    public Optional<EditorialDTO> obtenerEditorialPorId(Long id) {
        return editorialRepositorio.findById(id).map(this::convertirAEditorialDTO);
    }

    //  Crear una nueva editorial
    public EditorialDTO crearEditorial(EditorialDTO editorialDTO) {
        Editorial editorial = new Editorial();
        editorial.setNombre(editorialDTO.getNombre());
        editorial.setUbicacion(editorialDTO.getUbicacion());
        Editorial nuevaEditorial = editorialRepositorio.save(editorial);
        return convertirAEditorialDTO(nuevaEditorial);
    }
    //  Actualizar una editorial existente
    public Optional<EditorialDTO> actualizarEditorial(Long id, EditorialDTO editorialDTO) {
        return editorialRepositorio.findById(id).map(editorial -> {
            editorial.setNombre(editorialDTO.getNombre());
            editorial.setUbicacion(editorialDTO.getUbicacion());
            Editorial editorialActualizada = editorialRepositorio.save(editorial);
            return convertirAEditorialDTO(editorialActualizada);
        });
    }

    //  Eliminar una editorial
    public boolean eliminarEditorial(Long id) {
        if (editorialRepositorio.existsById(id)) {
            editorialRepositorio.deleteById(id);
            return true;
        }
        return false;
    }


    // ✅ Obtener todos los libros publicados por una editorial
    public List<LibroDetalleDTO> obtenerLibrosPorEditorial(Long editorialId) {
        return libroRepositorio.findByEditorialId(editorialId)
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
