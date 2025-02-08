package com.jonathan.LibreriaApp.Libreria.repositories;

import com.jonathan.LibreriaApp.Libreria.entities.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial,Long> {

}
