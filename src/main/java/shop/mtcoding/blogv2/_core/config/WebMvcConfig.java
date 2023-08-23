package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

// WEB.XML // json yml xml등 중간언어들로 된 설정파일을 스프링이 자바로 바꿔서 실행해준다
// 사진 찾을때 /images/이름.png 일때 images 폴더를 찾아주게
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/images/**")
            .addResourceLocations("file:"+"./images/")
            .setCachePeriod(10) // 10초
            .resourceChain(true)
            .addResolver(new PathResourceResolver());

    }
    

}
