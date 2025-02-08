package com.jonathan.LibreriaApp.Libreria.repositories;

import com.jonathan.LibreriaApp.Libreria.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    // Buscar libros por Autor
    List<Libro> findByAutorId(Long autorId);

    // Buscar libros por Editorial
    List<Libro> findByEditorialId(Long editorialId);

    // Buscar libros por género
    List<Libro> findByGeneros_Id(Long generoId);


    // Buscar libros por título, autor o género en orden alfabético
    @Query("SELECT l FROM Libro l " +
            "WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termino, '%')) " +
            "ORDER BY l.titulo ASC")
    List<Libro> searchByTitulo(String termino);
}
