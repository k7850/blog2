package shop.mtcoding.blogv2.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request){
        Page<Board> boardPG = boardService.게시글목록보기(page);
        request.setAttribute("boardPG", boardPG); // 실무에선 밑에 것들 같은거랑 뭉쳐서 DTO 만들어서 1개로 관리하는게 좋다
        request.setAttribute("prevPage", boardPG.getNumber()-1);
        request.setAttribute("nextPage", boardPG.getNumber()+1);
        return "index";
    }


    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    // 1. 데이터 받기 (V)
    // 2. 인증체크 (:TODO)
    // 3. 유효성 검사 (:TODO)
    // 4. 핵심로직 호출 (V)
    // 5. view or data 응답} (V)
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        boardService.글쓰기(saveDTO, 1);
        return "redirect:/";
    }



}