package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter1> myFilter1(){
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1()); // @Bean은 new는 직접 해야함
        bean.addUrlPatterns("/*"); //  슬래시(/)로 시작하는 모든 주소에 발동
        bean.setOrder(0); // 낮은 번호부터 실행
        return bean; // @Bean 있으면 리턴하는걸 ioc컨테이너에 띄움
    }

}
