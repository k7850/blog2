package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test(){
        // User user = User.builder().username("asd").password("1234").email("er@saerf").build();
        User user = userRepository.findById(1).get();
        System.out.println("테스트"+user);
        user.setPassword("5555");
        userRepository.save(user);
        System.out.println("테스트"+user);
        em.flush();
        System.out.println("테스트"+userRepository.findById(1).get());
    } // rollback

}
