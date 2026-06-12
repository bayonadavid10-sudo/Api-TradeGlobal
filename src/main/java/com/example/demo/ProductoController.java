package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private MongoTemplate mongoTemplate;

   static class Producto {
    public String id;
    public String nombre;
    public String descripcion;
    public String categoria;
    public double precio;
    public String imagen; 

    public Producto() {}
        public Producto(String nombre, String descripcion, String categoria, double precio) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.categoria = categoria;
            this.precio = precio;
        }
    }

    // GET — todos los productos
    @GetMapping
    public List<Producto> getProductos() {
        return mongoTemplate.findAll(Producto.class, "productos");
    }

    // POST — crear producto
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        mongoTemplate.save(producto, "productos");
        return producto;
    }

    // PUT — actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable String id, @RequestBody Producto producto) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update()
            .set("nombre", producto.nombre)
            .set("descripcion", producto.descripcion)
            .set("categoria", producto.categoria)
            .set("precio", producto.precio);
        mongoTemplate.updateFirst(query, update, "productos");
        return ResponseEntity.ok(Map.of("mensaje", "Producto actualizado"));
    }

    // DELETE — eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, "productos");
        return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado"));
    }
}