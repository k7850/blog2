package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

// 요청 처리, 핵심로직을 호출, 응답 처리
@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 브라우저 Get / logout 요청을 함 (request)
    // 서버는 / 주소를 response의 헤더에 담음 (Location), 상태코드 302 
    // 브라우저는 Get / 로 재요청을 함 (request 2)
    // index 페이지 응답받고 렌더링
    @GetMapping("/logout")
    public @ResponseBody String logout(){
        session.invalidate();
        return Script.href("로그아웃 했습니다.", "/");
    }

    // C - V (DB 관여 안하니까 M은 없다)
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }
    
    @GetMapping("/check")
    // ResponseEntity : @ResponseBody랑 비슷한데 HTTP 응답을 반환 / 응답 상태 코드, 헤더 및 응답 본문(body)을 설정하기 위해 사용
    public ResponseEntity<String> check(String username){
        User user = userService.중복체크(username);
        if (user != null) {
            return new ResponseEntity<String>("유저네임이 중복 되었습니다", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("유저네임을 사용할 수 있습니다", HttpStatus.OK);
    }


    // M - V - C
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO){
        // System.out.println(joinDTO.getPic().getOriginalFilename());
        // System.out.println(joinDTO.getPic().getSize());
        // System.out.println(joinDTO.getPic().getContentType());

        userService.회원가입(joinDTO);
        return "user/loginForm"; // persist 초기화
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO){ // @ResponseBody 여기 적어도 됨
        User sessionUser = userService.로그인(loginDTO);
        // if(sessionUser == null){
        //     throw new MyException("로그인 실패했습니다.");
        // } // 서비스에서 null을 리턴하는게 아니라 아예 에러로 던졌으니까 필요없음
        session.setAttribute("sessionUser", sessionUser); // session을 쓰면 Stateful서버
        return Script.href("로그인 성공", "/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request){
        User sessionUser = (User)session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO){
        // 1. 회원수정 (Service가 할 일)
        // 2. 수정된거랑 세션이랑 동기화
        User sessionUser = (User)session.getAttribute("sessionUser");
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }




}
