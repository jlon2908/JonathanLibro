package com.jonathan.LibreriaApp.Libreria.repositories;

import com.jonathan.LibreriaApp.Libreria.entities.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepositorio extends JpaRepository<Genero,Long> {

}
