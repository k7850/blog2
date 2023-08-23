package shop.mtcoding.blogv2.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.Image;
import shop.mtcoding.blogv2._core.vo.MyPath;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;

// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional // db 변경이 있으면 붙여야함
    public void 회원가입(JoinDTO joinDTO) {

        String fileName = Image.updateImage(joinDTO);

        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .picUrl(fileName) // "./images/"+fileName 로 경로까지 적으면 폴더변경불가
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
    public User 회원수정(UpdateDTO updateDTO, Integer id) {

        String fileName = Image.updateImage(updateDTO);

        // 1. 조회 (영속화)
        User user = userRepository.findById(id).get();

        // 2. 변경
        if(!updateDTO.getPassword().isEmpty()){
            user.setPassword(updateDTO.getPassword());
        }
        
        if(fileName != null){ // 사진 안넣으면 기존 사진 유지하게
            user.setPicUrl(fileName);
        }
        
        return user;
    } // 3. Transactional이면 flush 필요없음

    public User 중복체크(String username) {
        return userRepository.findByUsername(username);
    }


}
