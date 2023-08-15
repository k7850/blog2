package shop.mtcoding.blogv2.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRequest {

    @Getter @Setter @ToString
    public static class JoinDTO{
        private String username;
        private String password;
        private String email;
    }

    @Getter @Setter @ToString
    public static class LoginDTO{
        private String username;
        private String password;
    }

    @Getter @Setter @ToString
    public static class UpdateDto{
        private String password;
    }




}
