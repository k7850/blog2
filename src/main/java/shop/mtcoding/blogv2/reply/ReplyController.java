package shop.mtcoding.blogv2.reply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2.user.User;

@Controller
public class ReplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ReplyService replyService;

    @DeleteMapping("/api/reply/{id}/delete") // 딜리트매핑이니까 원랜 /delete 안적어도됨
    public @ResponseBody ApiUtil<String> delete(@PathVariable Integer id){
        // 1. 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null){
            throw new MyApiException("인증되지 않았습니다");
        }

        // 2. 핵심로직
        replyService.삭제하기(id, sessionUser.getId());

        // 3. 응답
        return new ApiUtil<String>(true, "댓글삭제완료");
    }

    @PostMapping("/api/reply/save")
    public @ResponseBody ApiUtil<String> save(@RequestBody ReplyRequest.SaveDTO saveDTO){
        // System.out.println("테스트 : "+saveDTO.getBoardId());
        // System.out.println("테스트 : "+saveDTO.getComment());
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null){
            // return new ApiUtil<String>(false, "인증이 되지 않았습니다");
            // 오류는 전부 에러로 넘기기로 했음
            throw new MyApiException("인증되지 않았습니다");
        }
        replyService.리플쓰기(saveDTO, sessionUser.getId());
        return new ApiUtil<String>(true, "댓글쓰기 성공");
    }



    // @PostMapping("/reply/{id}/delete")
    // public String delete(@PathVariable Integer id, Integer boardId){
    //     replyService.삭제하기(id);
    //     return "redirect:/board/" + boardId;
    // }

    // @PostMapping("/reply/save")
    // public String update(ReplyRequest.SaveDTO saveDTO) {
    //     replyService.리플쓰기(saveDTO, ((User)session.getAttribute("sessionUser")).getId());
    //     return "redirect:/board/" + saveDTO.getBoardId();
    // }


}
