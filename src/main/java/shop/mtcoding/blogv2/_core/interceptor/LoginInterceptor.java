package shop.mtcoding.blogv2._core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

// 인터셉터 생성
public class LoginInterceptor implements HandlerInterceptor {

    // return true면 컨트롤러 메서드 진입
    // return false면 요청 종료됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // System.out.println("테스트 : LoginInterceptor preHandle");

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        String startEndPoint = request.getRequestURI().split("/")[1];


        if(sessionUser != null){
            return true;
        }

        if(startEndPoint.equals("api")){
            response.setHeader("Content-Type", "application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            ApiUtil<String> apiUtil = new ApiUtil<>(false, "인증이 필요합니다");
            String responseBody = new ObjectMapper().writeValueAsString(apiUtil);
            out.println(responseBody);
        }else{
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(Script.href("로그인이 필요합니다", "/loginForm"));
        }
        return false; // 이거만 있으면 그냥 하얀화면만 떠서 사용자가 고장난줄 안다
            


    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    @Nullable ModelAndView modelAndView) throws Exception {
        // System.out.println("테스트 : LoginInterceptor postHandle");
    }
    
}
