package tech.matiaszelarayan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.matiaszelarayan.service.ChatService;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // Clave para que cualquier frontend pueda consultarlo sin bloqueos
public class ChatController {

    private final ChatService chatService;

    // Inyectamos el servicio que ya creamos
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> chatear(@RequestBody Map<String, String> request) {
        String pregunta = request.get("pregunta");

        // Aplicamos prevención de errores básicos para no procesar consultas vacías
        if (pregunta == null || pregunta.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "La pregunta no puede estar vacía"));
        }

        String respuesta = chatService.responderPregunta(pregunta);
        return ResponseEntity.ok(Map.of("respuesta", respuesta));
    }
}