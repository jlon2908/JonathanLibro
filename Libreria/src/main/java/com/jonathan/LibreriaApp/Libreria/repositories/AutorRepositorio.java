package com.jonathan.LibreriaApp.Libreria.repositories;

import com.jonathan.LibreriaApp.Libreria.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long> {
}
