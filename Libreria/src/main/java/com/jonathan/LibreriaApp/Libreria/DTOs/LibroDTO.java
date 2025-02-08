package com.jonathan.LibreriaApp.Libreria.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long autorId;
    private Long editorialId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
