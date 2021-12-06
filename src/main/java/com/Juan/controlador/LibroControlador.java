package com.Juan.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Juan.Modelos.AjaxResponseLibros;
import com.Juan.entidades.Autor;
import com.Juan.entidades.Editorial;
import com.Juan.entidades.Libro;
import com.Juan.servicio.AutorServicio;
import com.Juan.servicio.EditorialServicio;
import com.Juan.servicio.LibroService;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
	@Autowired
	private LibroService libroServicio;
	@Autowired
	private AutorServicio autorServicio;
	@Autowired
	private EditorialServicio editorialServicio;

	@GetMapping("/registro")
	public String formulario(ModelMap modelo) {

		try {
			List<Autor> autores = autorServicio.listarTodos();

			modelo.addAttribute("autores", autores);
			List<Editorial> editoriales = editorialServicio.listarTodos();

			modelo.addAttribute("editoriales", editoriales);
		} catch (Exception e) {
			System.out.println("error");
		}

		return "cargar-libro";

	}

	@PostMapping("/registro")
	public String guardarLibro(ModelMap modelo, @RequestParam(required = false) Long isbn,
			@RequestParam(required = false) String titulo, @RequestParam(required = false) Integer anio,
			@RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String autor,
			@RequestParam(required = false) String editorial) throws Exception {

		try {

			libroServicio.guardarLibro(isbn, titulo, anio, ejemplares, autor, editorial);

			modelo.put("exito", "Registro exitoso");
			return "cargar-libro";
		} catch (Exception e) {

			modelo.put("error", "Falto algun dato");
			return "cargar-libro";
		}
	}

	@GetMapping("/lista")
	public String lista(ModelMap modelo) {

		List<Libro> libros = libroServicio.listarTodos();

		modelo.addAttribute("librosHtml", libros);

		return "listar-libros";
	}

	// -------------------
	@GetMapping("/listarLibro")
	public String listarIndex() {

		return "listar";
	}

	@GetMapping("/listar")
	@ResponseBody
	public ResponseEntity<AjaxResponseLibros> listar() throws Exception {

		AjaxResponseLibros resultado = new AjaxResponseLibros();

		try {

			List<Libro> productos = libroServicio.listarActivos();
			for (Libro libro : productos) {
				System.out.println(libro);
			}
			resultado.setProductos(productos);

		} catch (Exception e) {
			resultado.setMensaje(e.getMessage());

			return ResponseEntity.badRequest().body(resultado);
		}

		return ResponseEntity.ok(resultado);

	}

	@GetMapping("/buscar/{nombre}")
	@ResponseBody
	public ResponseEntity<AjaxResponseLibros> listarPorNombre(@PathVariable String nombre) throws Exception {

		AjaxResponseLibros resultado = new AjaxResponseLibros();

		try {

			List<Libro> productos = libroServicio.listarPorNombre(nombre);

			resultado.setProductos(productos);

		} catch (Exception e) {
			resultado.setMensaje(e.getMessage());

			return ResponseEntity.badRequest().body(resultado);
		}

		return ResponseEntity.ok(resultado);

	}


//	@PostMapping("/editar/{id}")
//	public String mostrarFormularioDeEditar(@PathVariable("id") String id, ModelMap modelo,
//			@RequestParam(required = false) Long isbn, @RequestParam(required = false) String titulo,
//			@RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares,
//			@RequestParam(required = false) String autor, @RequestParam(required = false) String editorial)
//			throws Exception {
//		Libro libro = new Libro(isbn, titulo, anio, ejemplares);
//		try {
//			modelo.put("libro", libroServicio.actualizarLibro(libro));
//
//			modelo.put("exito", "Registro exitoso");
//			return "modificar-libro";
//		} catch (Exception e) {
//			modelo.put("error", "Falto algun dato");
//			return "modificar-libro";
//		}
//
//	}
//	
	
//Si esta ingresand0 y carga los datos 
	@GetMapping("/editar/{id}")
	public String mostrarFormularioDeEditar(@PathVariable("id") String id, ModelMap modelo) {
		
			
		modelo.put("libro", libroServicio.obtenerLibro(id));

	    modelo.addAttribute("autores", autorServicio.listarTodos());
		
		modelo.addAttribute("editoriales", editorialServicio.listarTodos());

		return "modificar-libro";
	}
	//este metodo me tien que guardar los datos en la base de datos
	@PostMapping("/editar/{id}")
	public String modificar(ModelMap modelo,@PathVariable("id") String id,
			@RequestParam(required = false) Long isbn, @RequestParam(required = false) String titulo,
			@RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares
			
			) {
		
		try {
			System.out.println("id en metodo post: "+id);
			System.out.println("isbn en metodo post: "+isbn);
			System.out.println("titulo en metodo post: "+titulo);
			//libroServicio.guardarLibro(isbn, titulo, anio, ejemplares, autor, editorial);
			
			libroServicio.modificar(id, isbn, titulo, anio, ejemplares);
			modelo.put("exito", "Modificacion exitosa");
			
			return "redirect:/libro/lista";
		} catch (Exception e) {
			modelo.put("error", "Falto algun dato");   
			return "modificar-libro";
		}
	}
	
	
	



	@GetMapping("/baja/{id}")
	public String baja(@PathVariable String id) {
				
		try {
			libroServicio.baja(id);
			return "redirect:/libro/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/alta/{id}")
	public String alta(@PathVariable String id) {
		
		try {
			libroServicio.alta(id);
			return "redirect:/libro/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}
	
	
	
	
	
	
	
	
	
}
