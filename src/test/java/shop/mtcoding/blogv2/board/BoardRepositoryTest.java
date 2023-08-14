package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Respository, EntityManager 메모리에 뜬다
public class BoardRepositoryTest {
    
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void save_test(){
        
        // 비영속 객체
        Board board = Board.builder()
        .title("제목6")
        .content("내용6")
        // .createdAt(Timestamp.valueOf(LocalDateTime.now()))
        .user(User.builder().id(1).build())
        .build();

        System.out.println("테스트:"+board);

        // 영속 객체
        boardRepository.save(board); // insert 자동으로 실행됨
        //db와 동기화됨
        System.out.println("테스트:"+board);
    }
}
