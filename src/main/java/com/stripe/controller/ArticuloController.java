package com.stripe.controller;


import com.stripe.dto.Mensaje;
import com.stripe.entity.Articulo;
import com.stripe.service.ArticuloService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articulo")
@CrossOrigin(origins = "*")
public class ArticuloController {

    @Autowired
    ArticuloService articuloService;

    @GetMapping("/lista")
    public ResponseEntity<List<Articulo>> lista(){
        List<Articulo> lista = articuloService.lista();
        return new ResponseEntity<List<Articulo>>(lista, HttpStatus.OK);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Articulo> detalle(@PathVariable("id") long id){
        if(!articuloService.existsId(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Articulo articulo = articuloService.getById(id).get();
        return new ResponseEntity<Articulo>(articulo, HttpStatus.OK);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> crear(@RequestBody Articulo articulo){
        if(StringUtils.isBlank(articulo.getNombre()))
            return new ResponseEntity<>(new Mensaje("nombre obligatorio"), HttpStatus.BAD_REQUEST);
        if((Integer)articulo.getPrecio() == null || articulo.getPrecio() < 1)
            return new ResponseEntity<>(new Mensaje("precio obligatorio"), HttpStatus.BAD_REQUEST);
        if(articuloService.existsNombre(articulo.getNombre()))
            return new ResponseEntity<>(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        articuloService.save(articulo);
        return new ResponseEntity<>(new Mensaje("artículo creado"), HttpStatus.CREATED);
    }
}
