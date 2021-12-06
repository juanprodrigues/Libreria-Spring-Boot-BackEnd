package com.Juan.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Juan.Modelos.AjaxResponseAutor;

import com.Juan.entidades.Autor;

import com.Juan.servicio.AutorServicio;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

	@Autowired
	private AutorServicio autorServicio;

	@GetMapping("/crear")
	public String formularioCrear() {
		return "crear-autor";
	}

	@PostMapping("/crear")
	public String crearAutor(ModelMap modelo, @RequestParam String nombre) throws Exception {
		try {

			autorServicio.guardar(nombre);

			modelo.put("exito", "Registro exitoso");
			return "crear-autor";
		} catch (Exception e) {

			modelo.put("error", "Falto algun dato");
			return "crear-autor";
		}
	}

	@GetMapping("/cargarautor")
	public String formulario() {

		return "cargar-autor";
	}

	@PostMapping("/cargarautor")
	public String lista(ModelMap modelo, @RequestParam String nombre) {

		List<Autor> autores = autorServicio.buscarPorNombre(nombre);

		modelo.addAttribute("autores", autores);

		modelo.put("exito", "Registro exitoso");
		return "cargar-autor";
	}

	@GetMapping("/lista")
	public String lista(ModelMap modelo) {

		List<Autor> libros = autorServicio.listarTodos();

		modelo.addAttribute("librosHtml", libros);

		return "listar-autor";
	}

	@GetMapping("/baja/{id}")
	public String baja(ModelMap modelo, @PathVariable String id) {

		try {
			autorServicio.baja(id);
			return "redirect:/autor/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@GetMapping("/alta/{id}")
	public String alta(ModelMap modelo, @PathVariable String id) {

		try {
			autorServicio.alta(id);
			return "redirect:/autor/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	// -------------------
	@GetMapping("/listarAutor")
	public String listarIndex() {

		return "listarA";
	}

	@GetMapping("/listar")
	@ResponseBody
	public ResponseEntity<AjaxResponseAutor> listar() throws Exception {

		AjaxResponseAutor resultado = new AjaxResponseAutor();

		try {

			List<Autor> productos = autorServicio.listarActivos();
//				for (Libro libro : productos) {
//					System.out.println(libro);
//				}
			resultado.setProductos(productos);

		} catch (Exception e) {
			resultado.setMensaje(e.getMessage());

			return ResponseEntity.badRequest().body(resultado);
		}

		return ResponseEntity.ok(resultado);

	}

	@GetMapping("/buscar/{nombre}")
	@ResponseBody
	public ResponseEntity<AjaxResponseAutor> listarPorNombre(@PathVariable String nombre) throws Exception {

		AjaxResponseAutor resultado = new AjaxResponseAutor();

		try {

			List<Autor> productos = autorServicio.listarPorNombre(nombre);

			resultado.setProductos(productos);

		} catch (Exception e) {
			resultado.setMensaje(e.getMessage());

			return ResponseEntity.badRequest().body(resultado);
		}

		return ResponseEntity.ok(resultado);

	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioDeEditar(@PathVariable("id") String id, ModelMap modelo) {

//		modelo.put("libro", libroServicio.obtenerLibro(id));
		Autor autorTraido = autorServicio.obtenerAutor(id);
		System.out.println(autorTraido.toString());
		modelo.addAttribute("autoresHtml", autorServicio.obtenerAutor(id));

//		modelo.addAttribute("editoriales", editorialServicio.listarTodos());

		return "modificar-autor";
	}

	// este metodo me tien que guardar los datos en la base de datos
	@PostMapping("/editar/{id}")
	public String modificar(ModelMap modelo, @PathVariable("id") String id,
			@RequestParam(required = false) String nombre) {

		try {
			System.out.println("id en metodo post: " +id);
			System.out.println("nombre en metodo post: " + nombre);

			// libroServicio.guardarLibro(isbn, titulo, anio, ejemplares, autor, editorial);

			autorServicio.modificar(id, nombre);
			modelo.put("exito", "Modificacion exitosa");

			return "redirect:/autor/lista";
		} catch (Exception e) {
			modelo.put("error", "Falto algun dato");
			return "modificar-autor";
		}
	}

}
