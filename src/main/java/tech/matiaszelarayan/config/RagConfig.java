package tech.matiaszelarayan.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class RagConfig {

    @Value("classpath:cv.json")
    private Resource cvResource;

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();

        try {
            // Leemos el contenido completo del JSON en UTF-8
            String cvContent = cvResource.getContentAsString(StandardCharsets.UTF_8);

            // Creamos un único documento con toda la información unificada
            Document cvDocument = new Document(cvContent);

            // Guardamos el documento en el almacén de vectores
            simpleVectorStore.add(List.of(cvDocument));

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo cv.json para el VectorStore", e);
        }

        return simpleVectorStore;
    }
}