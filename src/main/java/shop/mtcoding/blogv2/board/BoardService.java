package shop.mtcoding.blogv2.board;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.board.BoardRequest.UpdateDTO;
import shop.mtcoding.blogv2.user.User;

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

    public Board 상세보기(Integer id) {
        Optional<Board> boardOP = boardRepository.mFindByIdJoinRepliesInUser(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new RuntimeException("게시글" + id + "는 찾을 수 없습니다");
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
            throw new RuntimeException(id + "글 없음");
        }
    } // @Transactional 끝나면서 flush되면 더티체킹으로 자동 반영됨

    @Transactional
    public void 삭제하기(Integer id) {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("없는 게시글 id로 찾음");
        }
    }

}