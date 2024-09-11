package br.com.clepsidra.core.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiDefaultConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Clepsidra API")
                .version("1.0")
                .description("API para construção de sites com novelas baseadas na interação dinâmica entre leitores e autres")
                .termsOfService("http://www.clepsidra.com.br/termos-d-serv.html")
                .license(new License().name("Apache 2.0")
                        .url("http://www.apache.org/licenses/"))
        );
    }
}
