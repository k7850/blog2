package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import shop.mtcoding.blogv2._core.interceptor.LoginInterceptor;

// WEB.XML // json yml xml등 중간언어들로 된 설정파일을 스프링이 자바로 바꿔서 실행해준다
// 사진 찾을때 /images/이름.png 일때 images 폴더를 찾아주게
// 기본은 src\main\resources\static폴더
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/images/**") //  /images/이름    파일을 요청할때는
            .addResourceLocations("file:"+"./images/") // 최상위경로에 images폴더   에서 찾게
            .setCachePeriod(10) // 10초
            .resourceChain(true)
            .addResolver(new PathResourceResolver());

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/**")
                .addPathPatterns("/user/update", "/user/updateForm")
                .addPathPatterns("/board/**") // 발동 조건
                .excludePathPatterns("/board/{id:[0-9]+}"); // 발동 제외
    }
    
    

}
