package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Permite peticiones desde el navegador
public class UsuarioController {

    // ── Clase interna Usuario ──────────────────────────
    static class Usuario {
        public int id;
        public String nombre;
        public String email;
        public int edad;

        public Usuario(int id, String nombre, String email, int edad) {
            this.id     = id;
            this.nombre = nombre;
            this.email  = email;
            this.edad   = edad;
        }
    }

    // ── Array de usuarios ─────────────────────────────
    private static final List<Usuario> usuarios = Arrays.asList(
        new Usuario(1, "Ana García",     "ana.garcia@email.com",    28),
        new Usuario(2, "Carlos López",   "carlos.lopez@email.com",  34),
        new Usuario(3, "María Torres",   "maria.torres@email.com",  22),
        new Usuario(4, "Luis Martínez",  "luis.martinez@email.com", 41),
        new Usuario(5, "Sofía Ramírez",  "sofia.ramirez@email.com", 19),
        new Usuario(6, "Jorge Pérez",    "jorge.perez@email.com",   55),
        new Usuario(7, "Valentina Cruz", "val.cruz@email.com",      31)
    );

    // ── GET /api/usuarios → devuelve todos ────────────
    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // ── GET /api/usuarios/{id} → devuelve uno ─────────
    @GetMapping("/usuarios/{id}")
    public Usuario getUsuario(@PathVariable int id) {
        return usuarios.stream()
            .filter(u -> u.id == id)
            .findFirst()
            .orElse(null);
    }
}