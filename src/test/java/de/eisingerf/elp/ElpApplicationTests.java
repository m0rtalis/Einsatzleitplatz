package de.eisingerf.elp;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileOutputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ElpApplicationTests {

    private static final String API_DOCS_PATH = "/v3/api-docs.yaml";

    private static final File openApiFile = new File("./src/main/resources/apidocs/openapi.yaml");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void generateOpenAPIDefinition() throws Exception {
        // TODO: Use instead https://github.com/springdoc/springdoc-openapi-gradle-plugin
        assertFalse(openApiFile.isAbsolute());

        final MvcResult response =
                mockMvc.perform(get(API_DOCS_PATH)).andExpect(status().isOk()).andReturn();

        assertNotNull(response);
        assertNotNull(response.getResponse());
        final byte[] file = response.getResponse().getContentAsByteArray();
        assertNotEquals(0, file.length);
        //noinspection ResultOfMethodCallIgnored
        openApiFile.getParentFile().mkdirs();
        try (final FileOutputStream fos = new FileOutputStream(openApiFile)) {
            fos.write(file);
        }
    }
}
