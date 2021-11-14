/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.ejercicio1.spring.repositorios;

import egg.ejercicio1.spring.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    @Query("SELECT c FROM Libro c WHERE c.id= :id")
    public Libro buscarLibroPorId(@Param("id") String id);
    
    @Query("SELECT c FROM Libro c WHERE c.titulo= :titulo")
    public Libro buscarLibroPorNombre(@Param("titulo") String titulo);
    
    @Query("SELECT c FROM Libro c WHERE c.isbn= :isbn")
    public Libro buscarLibroPorIsbn(@Param("isbn") Long isbn);

}
