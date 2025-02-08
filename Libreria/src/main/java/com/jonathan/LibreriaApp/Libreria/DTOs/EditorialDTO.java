package com.jonathan.LibreriaApp.Libreria.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorialDTO {
    private Long id;
    private String nombre;
    private String ubicacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
