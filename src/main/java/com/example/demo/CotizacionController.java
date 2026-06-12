package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CotizacionController {

    @Autowired
    private MongoTemplate mongoTemplate;

    static class Cotizacion {
        public String id;
        public String email;
        public String fecha;

        public Cotizacion() {}
        public Cotizacion(String email) {
            this.email = email;
            this.fecha = LocalDateTime.now().toString();
        }
    }

    // GET — obtener todas las cotizaciones
    @GetMapping("/cotizaciones")
    public List<Cotizacion> getCotizaciones() {
        return mongoTemplate.findAll(Cotizacion.class, "cotizaciones");
    }

    // GET por ID — obtener una cotización
    @GetMapping("/cotizaciones/{id}")
    public ResponseEntity<?> getCotizacion(@PathVariable String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Cotizacion c = mongoTemplate.findOne(query, Cotizacion.class, "cotizaciones");
        if (c == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }

    // POST — crear cotización
    @PostMapping("/cotizaciones")
    public Cotizacion crearCotizacion(@RequestBody Map<String, String> body) {
        Cotizacion c = new Cotizacion(body.get("email"));
        mongoTemplate.save(c, "cotizaciones");
        return c;
    }

    // PUT — actualizar email de una cotización
    @PutMapping("/cotizaciones/{id}")
    public ResponseEntity<?> actualizarCotizacion(@PathVariable String id, @RequestBody Map<String, String> body) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("email", body.get("email"));
        mongoTemplate.updateFirst(query, update, "cotizaciones");
        return ResponseEntity.ok(Map.of("mensaje", "Actualizado correctamente"));
    }

    // DELETE — eliminar una cotización
    @DeleteMapping("/cotizaciones/{id}")
    public ResponseEntity<?> eliminarCotizacion(@PathVariable String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, "cotizaciones");
        return ResponseEntity.ok(Map.of("mensaje", "Eliminado correctamente"));
    }
}