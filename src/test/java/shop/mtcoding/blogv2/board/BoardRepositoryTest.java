package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Respository, EntityManager 메모리에 뜬다
public class BoardRepositoryTest {
    
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findAll_paging_test() throws JsonProcessingException{
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Board> boardPG = boardRepository.findAll(pageable);
        ObjectMapper om = new ObjectMapper();
        // ObjectMapper는 boardPG객체의 getter를 호출하면서 json을 만든다
        String json = om.writeValueAsString(boardPG); // 자바객체를 JSON으로 변환
        System.out.println(json);
    }

    @Test
    public void findAll_test(){
        System.out.println("조회직전");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("조회후 : Lazy");
        // db결과 행 5개 -> java객체 5개
        // Board(id=1, title=제목1, content=내용1, user(id=1, 나머지는 null))
        System.out.println("보드id : "+boardList.get(0).getId()); // 보드아이디 1번
        System.out.println("유저id : "+boardList.get(0).getUser().getId()); // 유저아이디 1번
        System.out.println("유저id : "+boardList.get(0).getUser().getUsername()); // 지연로딩(Lazy)
        // 영속화된 객체의 null을 참조하려고 하면 이시점에서 유저아이디로 db조회해서 찾아옴
    }

    @Test
    public void mfindAll_test(){
        System.out.println("=======123===========");
        List<Board> list = new ArrayList<>();
        list = boardRepository.mfindAll();
        // System.out.println("테스트"+list);
        for (Board board : list) { 
            System.out.println("테스트 : " + board);
        }
    }




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
