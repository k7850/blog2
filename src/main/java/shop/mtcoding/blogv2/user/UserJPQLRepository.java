package shop.mtcoding.blogv2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJPQLRepository extends JpaRepository<User, Integer>{
    
    // executeQuery
    @Query(value = "select u from User u where u.id = :id")
    Optional<User> mFindById(@Param("id") Integer id);

    // executeQuery
    @Query(value = "select u from User u where u.username = :username") // 이 줄 없어도 작동한다.(NamedQuery)
    //  메서드 이름을 문법 규칙대로 만들면 저절로 작성해줌. 그래도 문법을 배워야하니까 그냥 간단한거만 사용하고 웬만하면 직접작성하자
    // https://velog.io/@sunnamgung8/JPA-%EC%BF%BC%EB%A6%AC-%EB%A9%94%EC%86%8C%EB%93%9C-%EA%B8%B0%EB%8A%A5
    User findByUsername(@Param("username") String username);

    // insert, update, delete는 JPQL 사용 못함
    // @Modifying // executeUpdate
    // @Query(value = "insert into user_tb(created_at, email, password, username) values(now(), :email, :password, :username)")
    // void mSave(@Param("username") String username, @Param("password") String password, @Param("email") String email); 
}
