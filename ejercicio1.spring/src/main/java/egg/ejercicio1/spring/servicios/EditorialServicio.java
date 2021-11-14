/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package egg.ejercicio1.spring.servicios;

import egg.ejercicio1.spring.Errores.ErrorServicio;
import egg.ejercicio1.spring.entidades.Editorial;
import egg.ejercicio1.spring.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Service
public class EditorialServicio {
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void registrarEditorial(String nombre) throws ErrorServicio{
        validarEditorial(nombre);
        Editorial editorial=new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        
        editorialRepositorio.save(editorial);
    }
    
    @Transactional
    public void modificarEditorial(String nombre, String id)throws ErrorServicio{
        validarEditorial(nombre);
        Optional<Editorial> respuesta=editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial=respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("No se encontró la editorial");
        }
    }
    
    @Transactional
    public void habilitarEditorial(String nombre,String id)throws ErrorServicio{
        validarEditorial(nombre);
        Optional<Editorial> respuesta=editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial=respuesta.get();
            editorial.setAlta(true);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("No se encontró la editorial");
        }
    }
    
    @Transactional
    public void deshabilitarEditorial(String nombre,String id)throws ErrorServicio{
        validarEditorial(nombre);
        Optional<Editorial> respuesta=editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial=respuesta.get();
            editorial.setAlta(false);
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("No se encontró la editorial");
        }
    }
    
    public List<Editorial> editoriales(){
        List<Editorial> editoriales=editorialRepositorio.findAll();
        return editoriales;
    }
    
    public void validarEditorial(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la editorial no puede estar vacío");
        }
    }
    
}
