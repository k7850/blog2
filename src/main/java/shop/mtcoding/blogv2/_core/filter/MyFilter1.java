package shop.mtcoding.blogv2._core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;


public class MyFilter1 implements Filter { // javax.servlet.Filter 임포트해야함
    
    // 컴퍼먼트스캔이 안돼면 @Autowired 가 안됨
    // 컴퍼먼트스캔 할때 빈 생성자를 때리면서 new함
    // 빈생성자가 없으면 매개변수를 ioc컨테이너에서 찾음

    @Override // 리퀘스트 리스펀스 톰캣에서 받음 / 체인 : 다음필터로 전달, 없으면 디스패쳐서블릿으로
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // System.out.println("테스트 : ServletRequest : "+(request instanceof ServletRequest));
        // System.out.println("테스트 : HttpServletRequest : "+(request instanceof HttpServletRequest));

        HttpServletRequest req = (HttpServletRequest) request; // ServletRequest를 HttpServletRequest(기능이 더많음)로 다운캐스팅
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1. IP 로그 남기기
        // System.out.println("테스트 : 접속자 ip:"+req.getRemoteAddr());
        // System.out.println("테스트 : 접속자 user agent:"+req.getHeader("User-Agent"));

        // 2. 블랙리스트 추방
        if(req.getHeader("User-Agent").contains("Whale")){
            //resp.setContentType("text/html; charset=utf-8");
            resp.setHeader("Content-Type", "text/html; charset=utf-8");
            PrintWriter out = resp.getWriter();

            req.setAttribute("name", "홍길동");
            out.println("<h1>웨일이면 필터 테스트 : "+req.getAttribute("name")+"</h1>");
            return;
        }

        chain.doFilter(request, response); // 다음 필터로 request, response 전달 ..필터 없으면 D.S.로 전달

    }

}
