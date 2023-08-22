package shop.mtcoding.blogv2.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDto;

// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional // db 변경이 있으면 붙여야함
    public void 회원가입(JoinDTO joinDTO) {
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .build();
        userRepository.save(user); // 내부적으로 em.persist 가 실행되고 영속화
    }

    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 유저네임 검증
        if(user==null){
            throw new MyException("유저네임이 없습니다");
        }
        
        // 패스워드 검증
        if(!user.getPassword().equals(loginDTO.getPassword())){
            throw new MyException("패스워드가 틀렸습니다");
        }
        
        return user;
    }


    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User 회원수정(UpdateDto updateDTO, Integer id) {
        // 1. 조회 (영속화)
        User user = userRepository.findById(id).get();

        // 2. 변경
        user.setPassword(updateDTO.getPassword());

        
        return user;
    } // 3. Transactional이면 flush 필요없음

    public User 중복체크(String username) {
        return userRepository.findByUsername(username);
    }


}
