package com.Juan.Modelos;


import java.util.*;

import com.Juan.entidades.Libro;

import lombok.Data;

@Data
public class AjaxResponseLibros {

    String mensaje;
    List<Libro> productos;
    Libro producto;
}
