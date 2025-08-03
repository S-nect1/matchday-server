package com.example.moim.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("BearerAuth");

        return new OpenAPI()
                .info(new Info() // API 문서의 기본 정보
                        .title("MatchDay API")
                        .description("MatchDay 백엔드 API 문서")
                        .version("v1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", bearerAuthScheme))
                .addSecurityItem(securityRequirement);
    }

    private GroupedOpenApi buildGroupedOpenApi(String group, String basePackage) {
        return GroupedOpenApi.builder()
                .group(group)
                .pathsToMatch("/api/v1/**")
                .packagesToScan(basePackage)
                .build();
    }

    @Bean
    public GroupedOpenApi moinApi() {
        return buildGroupedOpenApi("모임 관련 API @오남의", "com.example.moim.club.controller");
    }

    @Bean
    public GroupedOpenApi notificationApi() {
        return buildGroupedOpenApi("알림 관련 API @최예빈", "com.example.moim.notification.controller");
    }

    @Bean
    public GroupedOpenApi matchApi() {
        return buildGroupedOpenApi("매치 관련 API @문호주", "com.example.moim.match.controller");
    }

    @Bean
    public GroupedOpenApi statisticApi() {
        return buildGroupedOpenApi("전적 관련 API@문호주", "com.example.moim.statistic.controller");
    }

    @Bean
    public GroupedOpenApi scheduleApi() {
        return buildGroupedOpenApi("스케줄 관련 API@오남의", "com.example.moim.schedule.controller");
    }

    @Bean
    public GroupedOpenApi userApi() {
        return buildGroupedOpenApi("유저 관련 API@최예빈", "com.example.moim.user.controller");
    }
}
