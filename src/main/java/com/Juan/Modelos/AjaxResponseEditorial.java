package com.Juan.Modelos;


import java.util.*;

import com.Juan.entidades.Editorial;


import lombok.Data;

@Data
public class AjaxResponseEditorial {

    String mensaje;
    List<Editorial> productos;
    Editorial producto;
}
