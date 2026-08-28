package tech.matiaszelarayan.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class RagConfig {

    // Le decimos a Spring que busque nuestro archivo JSON en la carpeta resources
    @Value("classpath:cv.json")
    private Resource cvResource;

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        // 1. Creamos el almacén de vectores en memoria
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();

        // 2. Leemos el archivo JSON. Spring AI extraerá el contenido de las claves que le indiquemos.
        JsonReader jsonReader = new JsonReader(cvResource,
                "puesto", "descripcion", "tags", "nombre", "sobre_mi", "rol");

        List<Document> documentos = jsonReader.get();

        // 3. Guardamos los documentos en nuestra base vectorial
        simpleVectorStore.add(documentos);

        return simpleVectorStore;
    }
}