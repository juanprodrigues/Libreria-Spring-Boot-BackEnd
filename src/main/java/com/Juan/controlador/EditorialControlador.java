package com.Juan.controlador;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.Juan.Modelos.AjaxResponseEditorial;
import com.Juan.entidades.Autor;
import com.Juan.entidades.Editorial;
import com.Juan.servicio.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
	@Autowired
	private EditorialServicio editorialServicio;

	
	
	@GetMapping("/crear")
	public String formularioCrear() {
		return "crear-editorial";
	}
	@PostMapping("/crear")
	public String crearAutor(ModelMap modelo, @RequestParam String nombre) throws Exception {
		try {

			editorialServicio.guardar(nombre);

			modelo.put("exito", "Registro exitoso");
			return "crear-autor";
		} catch (Exception e) {

			modelo.put("error", "Falto algun dato");
			return "crear-editorial";
		}
	}
//
//	@PostMapping("/cargarautor")
//	public String lista(ModelMap modelo, @RequestParam String nombre) {
//
//		List<Autor> autores = autorServicio.buscarPorNombre(nombre);
//
//		modelo.addAttribute("autores", autores);
//
//		modelo.put("exito", "Registro exitoso");
//		return "cargar-autor";
//	}
//
	
	
	@GetMapping("/lista")
	public String lista(ModelMap modelo) {

		List<Editorial> libros = editorialServicio.listarTodos();

		modelo.addAttribute("librosHtml", libros);

		return "listar-autor";
	}

	
	@GetMapping("/baja/{id}")
	public String baja(ModelMap modelo, @PathVariable String id) {

		try {
			editorialServicio.baja(id);
			return "redirect:/editorial/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@GetMapping("/alta/{id}")
	public String alta(ModelMap modelo, @PathVariable String id) {

		try {
			editorialServicio.alta(id);
			return "redirect:/editorial/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}
	
	
	// -------------------
	@GetMapping("/listarEditorial")
	public String listarIndex() {

		return "listarE";
	}

	@GetMapping("/listar")
	@ResponseBody
	public ResponseEntity<AjaxResponseEditorial> listar() throws Exception {

		AjaxResponseEditorial resultado = new AjaxResponseEditorial();

		try {

			List<Editorial> productos = editorialServicio.listarActivos();
//			for (Libro libro : productos) {
//				System.out.println(libro);
//			}
			resultado.setProductos(productos);

		} catch (Exception e) {
			resultado.setMensaje(e.getMessage());

			return ResponseEntity.badRequest().body(resultado);
		}

		return ResponseEntity.ok(resultado);

	}

	@GetMapping("/buscar/{nombre}")
	@ResponseBody
	public ResponseEntity<AjaxResponseEditorial> listarPorNombre(@PathVariable String nombre) throws Exception {

		AjaxResponseEditorial resultado = new AjaxResponseEditorial();

		try {

			List<Editorial> productos = editorialServicio.listarPorNombre(nombre);

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
		Editorial autorTraido = editorialServicio.obtenerEditorial(id);
		System.out.println(autorTraido.toString());
		modelo.addAttribute("editorialHtml", editorialServicio.obtenerEditorial(id));

//		modelo.addAttribute("editoriales", editorialServicio.listarTodos());

		return "modificar-editorial";
	}

	// este metodo me tien que guardar los datos en la base de datos
	@PostMapping("/editar/{id}")
	public String modificar(ModelMap modelo, @PathVariable("id") String id,
			@RequestParam(required = false) String nombre) {

		try {
			System.out.println("id en metodo post: " + id);
			System.out.println("nombre en metodo post: " + nombre);

			// libroServicio.guardarLibro(isbn, titulo, anio, ejemplares, autor, editorial);

			editorialServicio.modificar(id, nombre);
			modelo.put("exito", "Modificacion exitosa");

			return "redirect:/editorial/lista";
		} catch (Exception e) {
			modelo.put("error", "Falto algun dato");
			return "modificar-editorial";
		}
	}
	
	
	
	
}
