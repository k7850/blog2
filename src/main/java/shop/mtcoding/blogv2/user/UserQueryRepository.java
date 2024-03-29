package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserQueryRepository {

    @Autowired
    private EntityManager em;

    public void save(User user){
        em.persist(user); // 영속화 (영속성 컨텍스트)
    }

    // JPA findById
    public User findById(Integer id){
        return em.find(User.class, id);
    }


}