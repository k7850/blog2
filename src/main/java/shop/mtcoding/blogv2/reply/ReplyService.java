package shop.mtcoding.blogv2.reply;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 삭제하기(Integer id, Integer sessionUserId) {
        // 권한체크
        Optional<Reply> replyOP = replyRepository.findById(id);

        if(replyOP.isEmpty()){
            throw new MyApiException("삭제할 댓글이 없습니다");
        }

        Reply reply = replyOP.get();
        if(reply.getUser().getId() != sessionUserId){
            throw new MyApiException("해당 댓글을 삭제할 권한이 없습니다");
        }
        
        replyRepository.deleteById(id);
    }
    
    @Transactional
    public void 리플쓰기(ReplyRequest.SaveDTO saveDTO, Integer id) {

        // 1. board id 존재유무 (fk로 제약조건까지 없었으면 유령데이터가 들어감)

        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(Board.builder().id(saveDTO.getBoardId()).build())
                .user(User.builder().id(id).build())
                .build();
        replyRepository.save(reply);
    }

}
