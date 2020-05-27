package com.yc.fresh.api.conf;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by quyang on 2018/4/20.
 */
@Configuration
public class Swagger2 {

    @Value("${swagger}")
    private boolean swagger;

    private static final String basePkg = "com.yc.fresh.api.rest";

    @Bean
    public Docket createRestApi() {
        List<Parameter> parameters = new ArrayList<>();
        Predicate<RequestHandler> handlerPredicate = RequestHandlerSelectors.basePackage(basePkg);
        ApiInfo apiInfo = apiInfo();
        parameters.add(new ParameterBuilder()
                .name("tk")
                .description("认证token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build());
        return new Docket(DocumentationType.SWAGGER_2).enable(swagger)
                .apiInfo(apiInfo)
                .select()
                //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(handlerPredicate)
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("FRESH SERVICE API")
                .description("FRESH服务接口文档")
                .contact(new Contact("quy", "", "hb_quyang@sina.com"))
                .version("1.0")
                .build();
    }

}
