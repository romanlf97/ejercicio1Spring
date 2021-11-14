/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.ejercicio1.spring.controladores;

import egg.ejercicio1.spring.Errores.ErrorServicio;
import egg.ejercicio1.spring.entidades.Autor;
import egg.ejercicio1.spring.entidades.Editorial;
import egg.ejercicio1.spring.entidades.Libro;
import egg.ejercicio1.spring.repositorios.AutorRepositorio;
import egg.ejercicio1.spring.repositorios.EditorialRepositorio;
import egg.ejercicio1.spring.repositorios.LibroRepositorio;
import egg.ejercicio1.spring.servicios.AutorServicio;
import egg.ejercicio1.spring.servicios.EditorialServicio;
import egg.ejercicio1.spring.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Controller
@RequestMapping("/")
public class InicioControlador {

    @Autowired
    private AutorServicio autorServicio;
    /// No es correcto usar el repositorio aca, deberia usar el servicio
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @GetMapping("/libro")
    public String libro(ModelMap modelo) {
        List<Libro> libros=libroServicio.libros();
        modelo.addAttribute("libros",libros);
        return "libro.html";
    }

    @GetMapping("/libro/modificar/{id}")
    public String libroModificar(@PathVariable String id,ModelMap modelo) { 
        modelo.put("libro", libroRepositorio.buscarLibroPorId(id));
        List<Autor> autores = autorServicio.autores();
        List<Editorial> editoriales=editorialServicio.editoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libroModificar.html";
    }
    
    @PostMapping("/libro/modificar/{id}")
    public String libroMod(@PathVariable String id , ModelMap modelo, @RequestParam String titulo,@RequestParam Long isbn,@RequestParam Integer anio , @RequestParam Integer ejemplares,@RequestParam Integer ejemplaresPrestados,@RequestParam String autor,@RequestParam String editorial) throws ErrorServicio {
        
        libroServicio.modificarLibro(autor, editorial, id, titulo, isbn, anio, ejemplares, ejemplaresPrestados);
        return "libroModificar.html";
    }

    @GetMapping("libro/agregar")
    public String libroAgregar(ModelMap modelo) {
        List<Autor> autores = autorServicio.autores();
        List<Editorial> editoriales=editorialServicio.editoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libroAgregar.html";
    }
    
    @PostMapping("libro/agregar")
    public String libroAgregar(ModelMap modelo, @RequestParam String titulo,@RequestParam Long isbn,@RequestParam Integer anio , @RequestParam Integer ejemplares,@RequestParam Integer ejemplaresPrestados,@RequestParam (required = false) String autor,@RequestParam(required = false) String editorial ) {
        try {
            System.out.println("titulo: "+titulo);
            System.out.println("isbn: "+isbn);
            System.out.println("año: "+anio);
            System.out.println("ejemplares: "+ejemplares);
            System.out.println("ejemplaresPrestados: "+ejemplaresPrestados);
            System.out.println("autor: "+autor);
            System.out.println("editorial :"+editorial);
            
            libroServicio.registrarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
            modelo.put("exito", "Registro exitoso");
            return "libroAgregar.html";
        } catch (Exception e) {
            modelo.put("error", "Error falta completar algunos campos");
            return "libro.html";
        }
    }

    @GetMapping("/autor")
    public String autor(ModelMap modelo) {
        List<Autor> autores=autorServicio.autores();
        modelo.addAttribute("autores", autores);
        return "autor.html";
    }

    @GetMapping("/autor/modificar/{id}")
    public String autorModificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("autor", autorRepositorio.buscarAutorPorId(id));
        return "autorModificar.html";
    }

    @PostMapping("/autor/modificar/{id}")
    public String autorMod(@PathVariable String id,ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido) throws ErrorServicio {
        try {
            autorServicio.modificarAutor(nombre+" "+apellido,id);
            modelo.put("exito","Modificación exitosa");
            return "autor.html";
        } catch (Exception e) {
            modelo.put("error","Modificación erronea");
            return "autor.html";
        }
    }

    @GetMapping("autor/agregar")
    public String autorAgregar() {
        return "autorAgregar.html";
    }

    @PostMapping("autor/agregar")
    public String autorAg(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido) throws ErrorServicio {
        try {
            autorServicio.registrarAutor(nombre + " " + apellido);
            modelo.put("exito", "Registro exitoso");
            return "autorAgregar.html";
        } catch (Exception e) {
            modelo.put("error", "Error falta completar algunos campos");
            return "index.html";
        }
    }

    @GetMapping("/editorial")
    public String editorial(ModelMap modelo) {
        List<Editorial> editoriales=editorialServicio.editoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial.html";
    }

    @GetMapping("/editorial/modificar/{id}")
    public String editorialModificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("editorial", editorialRepositorio.buscarEditorialPorId(id));
        return "editorialModificar.html";
    }
    
    @PostMapping("/editorial/modificar/{id}")
    public String editorialMod(@PathVariable String id,ModelMap modelo, @RequestParam String nombre) throws ErrorServicio {
        try {
            editorialServicio.modificarEditorial(nombre,id);
            modelo.put("exito","Modificación exitosa");
            return "editorial.html";
        } catch (Exception e) {
            modelo.put("error","Fallo al modificar");
            return "editorial.html";
        }
    }

    @GetMapping("/editorial/agregar")
    public String editorialAgregar() {
        return "editorialAgregar.html";
    }

    @PostMapping("/editorial/agregar")
    public String editorialAg(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio {
        try {
            editorialServicio.registrarEditorial(nombre);
            modelo.put("exito", "Registro exitoso");
            return "editorialAgregar.html";
        } catch (Exception e) {
            modelo.put("error", "Error falta completar algunos campos");
            return "index.html";
        }
    }
    
    @GetMapping("/autor/baja/{id}")
    public String bajaAutor(@PathVariable String id){
        try {
            String nombre=autorRepositorio.buscarAutorPorId(id).getNombre();
            autorServicio.deshabilitarAutor(nombre, id);
            return "autor.html";
        } catch (Exception e) {
            return "autor.html";
        }
    }
    
    @GetMapping("/editorial/baja/{id}")
    public String bajaEditorial(@PathVariable String id){
        try {
            String nombre=editorialRepositorio.buscarEditorialPorId(id).getNombre();
            editorialServicio.deshabilitarEditorial(nombre, id);
            return "editorial.html";
        } catch (Exception e) {
            return "editorial.html";
        }
    }
}
