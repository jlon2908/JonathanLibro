package com.jonathan.LibreriaApp.Libreria.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDetalleDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String autorNombre;
    private String editorialNombre;
    private List<String> generos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
