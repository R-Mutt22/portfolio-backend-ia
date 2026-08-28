package tech.matiaszelarayan.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    // Inyección de dependencias por constructor (Buena práctica SOLID)
    public ChatService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    public String responderPregunta(String preguntaUsuario) {
        // 1. Buscar en nuestro VectorStore la información que coincida con la pregunta
        // withTopK(3) significa que traerá los 3 fragmentos de tu CV más relevantes
        List<Document> documentosRelevantes = vectorStore.similaritySearch(
                SearchRequest.builder().query(preguntaUsuario).topK(3).build()
        );

        // 2. Unir esos fragmentos encontrados en un solo texto
        String contexto = documentosRelevantes.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));

        // 3. El "System Prompt": Las reglas estrictas para Gemini
        String instrucciones = """
                Sos el asistente virtual de Matías Gabriel Zelarayán, un Backend Developer.
                Tu objetivo es responder preguntas de reclutadores sobre su perfil y experiencia.
                Utiliza ÚNICAMENTE la siguiente información de contexto para responder.
                Si la respuesta no está en el contexto, responde amablemente que no tienes esa
                información y sugiere contactar a Matías a matiaszelarayandev@gmail.com.
                
                Contexto sobre Matías:
                {contexto}
                """;

        // 4. Llamamos a Gemini pasándole las reglas, el contexto y la pregunta
        return chatClient.prompt()
                .system(systemSpec -> systemSpec.text(instrucciones).param("contexto", contexto))
                .user(preguntaUsuario)
                .call()
                .content();
    }
}