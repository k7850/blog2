package shop.mtcoding.blogv2.reply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

@Controller
public class ReplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, Integer boardId){
        replyService.삭제하기(id);
        return "redirect:/board/" + boardId;
    }

    @PostMapping("/reply/save")
    public String update(ReplyRequest.SaveDTO saveDTO) {
        replyService.리플쓰기(saveDTO, ((User)session.getAttribute("sessionUser")).getId());
        return "redirect:/board/" + saveDTO.getBoardId();
    }


}
