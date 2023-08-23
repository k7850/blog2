package shop.mtcoding.blogv2.user;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRequest {

    @Getter @Setter @ToString
    public static class PicDTO{
        private MultipartFile pic;
    }

    @Getter @Setter @ToString
    public static class JoinDTO extends PicDTO{
        private String username;
        private String password;
        private String email;
        // private MultipartFile pic; // 상속
    }

    @Getter @Setter @ToString
    public static class LoginDTO{
        private String username;
        private String password;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO extends PicDTO{
        private String password;
        // private MultipartFile pic; // 상속
    }




}
