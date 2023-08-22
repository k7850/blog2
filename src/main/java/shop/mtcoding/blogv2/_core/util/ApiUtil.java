package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUtil<T> { // Json으로 응답하려고 만듬
    private boolean sucuess; // true
    private T data; // 댓글 쓰기 성공

    public ApiUtil(boolean sucuess, T data) {
        this.sucuess = sucuess;
        this.data = data;
    }
      
}
