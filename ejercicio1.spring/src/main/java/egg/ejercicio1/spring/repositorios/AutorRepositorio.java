/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.ejercicio1.spring.repositorios;

import egg.ejercicio1.spring.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
    @Query("SELECT c FROM Autor c WHERE c.id= :id")
    public Autor buscarAutorPorId(@Param("id") String id);
    
    @Query("SELECT c FROM Autor c WHERE c.nombre= :nombre")
    public Autor buscarAutorPorNombre(@Param("nombre") String nombre);
    
}
