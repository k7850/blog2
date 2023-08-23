package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.user.User;

/*
 * save, findById, findAll, count 저절로 만들어줌
 */
// 스프링이 실행될 때, BoardRepository의 구현체가 IoC 컨테이너에 생성된다.
public interface BoardRepository extends JpaRepository<Board, Integer>{

    // select * from board_tb b inner join user_tb u on b.user_id = u.id;
    // fetch를 붙히면 조인해서 쿼리 한번으로 찾음. 그걸 jpa가 파싱해서 user객체로 만들고 매핑해줌
    // fetch 없으면 그냥 select * from board_tb; 로 찾은 board목록의 user_id컬럼 다른거마다 또 찾아서 성능문제 (db의 테이블은 컬럼으로 다른테이블을 못가지니까(그냥 다른테이블의 pk만 적어두고))
    @Query("select b from Board b join fetch b.user")
    List<Board> mfindAll();

    @Query("select b from Board b join fetch b.user where b.id = :id")
    Board mFindById(@Param("id") Integer id);

    @Query("select b from Board b left join fetch b.replies r left join fetch r.user ru where b.id = :id")
    Optional<Board> mFindByIdJoinRepliesInUser(@Param("id") Integer id);
    

    
    @Query("SELECT b FROM Board b WHERE b.title LIKE :keyword OR b.content LIKE :keyword ORDER BY b.id DESC")
    Page<Board> find2(@Param("keyword") String keyword, Pageable pageable);

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
    // Spring이 자동으로 만들어준거 
    // title로 찾는데 Containing이니까 검색해서 Page해서 반환

    Page<Board> findByTitleContainingOrContentContaining(String keyword, String keyword2, Pageable pageable);
    
    Page<Board> findByTitleContainingOrContentContainingOrUserId(String keyword, String keyword2, Integer userId, Pageable pageable);

    

}
