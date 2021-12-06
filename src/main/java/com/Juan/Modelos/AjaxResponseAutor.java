package com.Juan.Modelos;


import java.util.*;

import com.Juan.entidades.Autor;


import lombok.Data;

@Data
public class AjaxResponseAutor {

    String mensaje;
    List<Autor> productos;
    Autor producto;
}
