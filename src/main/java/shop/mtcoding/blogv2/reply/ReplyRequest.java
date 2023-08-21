package shop.mtcoding.blogv2.reply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ReplyRequest {

    @Getter @Setter @ToString
    public static class SaveDTO{
        private String comment;
        private Integer BoardId;
    }
    
}
