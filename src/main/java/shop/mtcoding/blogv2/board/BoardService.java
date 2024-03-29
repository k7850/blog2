package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.board.BoardRequest.UpdateDTO;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.reply.ReplyRepository;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserRepository;

/*
 * 서비스 역할
 *  1. 비지니스 로직 처리(핵심 로직)
 *  2. 트랜잭션 관리
 *  3. 예외처리
 *  4. DTO 변환
 */
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();

        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }

    public Page<Board> 게시글목록보기(String keyword, Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findByTitleContaining(keyword, pageable);
    }

    

    public Page<Board> 게시글목록보기3(String keyword, Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.find2("%"+keyword+"%", pageable);
    }


    @Autowired
    private UserRepository userRepository;

    public Page<Board> 게시글목록보기2(String keyword, Integer page) {
        User user = userRepository.findByUsername(keyword);

        Integer userId = 0;
        if(user != null){
            userId=user.getId();
        }

        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findByTitleContainingOrContentContainingOrUserId(keyword,keyword,userId, pageable);
    }

    public Board 상세보기(Integer id) {
        Optional<Board> boardOP = boardRepository.mFindByIdJoinRepliesInUser(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new MyException("게시글" + id + "는 찾을 수 없습니다");
        }
    }

    @Transactional
    public void 수정하기(Integer id, UpdateDTO DTO) {
        Optional<Board> boardOP = boardRepository.findById(id);

        if (boardOP.isPresent()) {
            Board board = boardOP.get();
            board.setTitle(DTO.getTitle());
            board.setContent(DTO.getContent());
        } else{
            throw new MyException(id + "글 없음");
        }
    } // @Transactional 끝나면서 flush되면 더티체킹으로 자동 반영됨

    @Transactional
    public void 삭제하기(Integer id) {
        List<Reply> replies = replyRepository.findByBoardId(id);
        for (Reply reply : replies) {
            reply.setBoard(null);
        }
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("없는 게시글 id로 찾음");
        }
    }

}