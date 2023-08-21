package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 삭제하기(Integer id) {
        try {
            replyRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("없는 리플");
        }
    }
    
    @Transactional
    public void 리플쓰기(ReplyRequest.SaveDTO saveDTO, Integer userId) {
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(Board.builder().id(saveDTO.getBoardId()).build())
                .user(User.builder().id(userId).build())
                .build();
        replyRepository.save(reply);
    }

}
