package shop.mtcoding.blogv2.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class BoardRequest {

    @Getter @Setter @ToString
    public static class SaveDTO{
        private String title;
        private String content;
    }
}
