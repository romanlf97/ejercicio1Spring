/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.ejercicio1.spring.servicios;

import egg.ejercicio1.spring.Errores.ErrorServicio;
import egg.ejercicio1.spring.entidades.Autor;
import egg.ejercicio1.spring.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void registrarAutor(String nombre) throws ErrorServicio {
        validarAutor(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);

        autorRepositorio.save(autor);
    }

    @Transactional
    public void modificarAutor(String nombre, String id) throws ErrorServicio {

        validarAutor(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("No se encontró el autor");
        }
    }
    
    public void habilitarAutor(String nombre, String id) throws ErrorServicio{
        validarAutor(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("No se encontró el autor");
        }
    }
    
    public void deshabilitarAutor(String nombre, String id) throws ErrorServicio{
        validarAutor(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("No se encontró el autor");
        }
    }
    
    public List<Autor> autores(){
        List <Autor> autores=autorRepositorio.findAll();
        return autores;
    }

    public void validarAutor(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del Autor no puede estar vacío");
        }
    }

}
