package com.jonathan.LibreriaApp.Libreria.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {
    private Long id;
    private String nombre;
    private String nacionalidad;
    private Integer edad;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
