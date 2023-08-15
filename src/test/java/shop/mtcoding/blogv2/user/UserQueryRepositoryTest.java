package shop.mtcoding.blogv2.user;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserQueryRepository.class)
@DataJpaTest // JpaRepository만 메모리에 올려준다
public class UserQueryRepositoryTest {
    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    // persist (영속화)
    @Test
    public void save_test(){
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();
        userQueryRepository.save(user); // 영속화
    }

    // 1차 캐시
    @Test
    public void findById_test(){
        System.out.println("1. pc(영속성 컨텍스트)는 비어있다.");
        userQueryRepository.findById(1);
        System.out.println("2. pc의 user 1은 영속화 되어있다");
        userQueryRepository.findById(1);
        em.clear();
        userQueryRepository.findById(1);
    }

    @Test
    public void update_test(){
        // update 알고리즘
        // 1. 업데이트 할 객체를 영속화
        // 2. 객체 상태 변경
        // 3. em.flush() or @Transactional의 정상 종료
        User user = userQueryRepository.findById(1); // pc(영속성 컨텍스트)에 없으니까 db에서 찾음
        System.out.println("1테스트:"+user);
        user.setEmail("ssarmango@nate.com");
        System.out.println("2테스트:"+user);
        System.out.println("3테스트:"+userQueryRepository.findById(1)); // 영속화 되어있으니까 db에서 안찾고 영속화된걸 pc에서 가져옴
        em.flush(); // 테스트코드라 롤백되니까 쓴거임. 실제론 db 변경 있어서 트랜잭셔녈 붙였을테니까 필요없음
        System.out.println("4테스트:"+user);
        System.out.println("5테스트:"+userQueryRepository.findById(1)); // 영속화 되어있으니까 db에서 안찾고 영속화된걸 pc에서 가져옴

    } // rollback



}
