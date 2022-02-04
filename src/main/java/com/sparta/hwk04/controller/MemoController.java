package com.sparta.hwk04.controller;


import com.sparta.hwk04.dto.MemoRequestDto;
import com.sparta.hwk04.model.Comment;
import com.sparta.hwk04.model.Memo;
import com.sparta.hwk04.repository.CommentRepository;
import com.sparta.hwk04.repository.MemoRepository;
import com.sparta.hwk04.repository.UserRepository;
import com.sparta.hwk04.security.UserDetailsImpl;
import com.sparta.hwk04.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@ResponseBody 이거 추가하면 타임리프 작동을 안한다

public class MemoController {

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    MemoService memoService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;




         //게시글 생성
//    @PostMapping("/api/memos")
//    public Memo createMemo(@RequestBody MemoRequestDto requestDto,
//                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        // 로그인 되어 있는 회원 테이블의 ID
//        Long userId = userDetails.getUser().getId();
//
//        Memo memo = memoService.createMemo(requestDto, userId);
////        return memoRepository.save(memo);
//        return memo;
//    }
//    //게시글 생성 페이지로 이동
//    @RequestMapping("/write")
//    public ModelAndView write(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("write.html");
//        return modelAndView;
//    }

    @GetMapping("/")
    public String getIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size=5) Pageable pageable){
        Page<Memo> memo = memoRepository.findAllByOrderByModifiedAtDesc(pageable);
        //memo.getTotalElements(); // 전체데이터 건수

        if(userDetails == null){
            model.addAttribute("user","null");
        }else{
            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        // 현재 페이지 넘버 - 4 (4는 임의로 정한값)을 뺀값을 보여줄 페이지 값에 첫번째 값으로 지정
        // 현재 페이지 넘버 + 4 을 더한값을 보여줄 페이지에서 끝값으로 표시
        int startPage = Math.max(1, memo.getPageable().getPageNumber() - 4); //그런데 음수가 나올수 있으니 max() 함수를 이용해 0보다 작은값은 나오지 않도록 만들어줌
        int endPage = Math.min(memo.getTotalPages(),memo.getPageable().getPageNumber() + 4); // 마찬가지로 최대페이지수를 초과하지않게 min()함수 걸어줌

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("memo",memo);
        return "index";
    }




//게시글 작성 페이지
@GetMapping("/write")
public String getNotice( Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
    Memo memo = new Memo();
    if(userDetails == null){
        model.addAttribute("user","null");
    }else{
        model.addAttribute("user",userDetails.getUser().getUsername());
    }
    model.addAttribute("memo", memo);
    return "write";
}
    // 게시글 작성
    @PostMapping("/write")
    public String createNotice(@AuthenticationPrincipal UserDetailsImpl userDetails,@ModelAttribute MemoRequestDto requestDto){
        requestDto.setUser(userDetails.getUser());
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        return "redirect:/";
    }


//    //게시글 전체 조회
//    @GetMapping("/api/memos")
//    public List<Memo> getMemo() {
//        return memoRepository.findAllByOrderByCreatedAtDesc();
//    }

//    //게시글 상세페이지 조회
//    @GetMapping("/detail/{id}")
//    public ModelAndView detailpage(@PathVariable("id") Long Id){
//        Optional<Memo> memo = memoRepository.findById(Id);
//        ModelAndView modelAndView = new ModelAndView("detail.html");
//        modelAndView.addObject("id",memo.get().getId());
//        modelAndView.addObject("title",memo.get().getTitle());
//        modelAndView.addObject("username",memo.get().getUsername());
//        modelAndView.addObject("contents",memo.get().getContents());
//        modelAndView.addObject("createdAt",memo.get().getCreatedAt());
//
//        return modelAndView;
//    }

    //게시글 한개 조회페이지
    @GetMapping("/detail/{id}")
    public String getOneMemo(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){

        Memo memo = memoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> comment = commentRepository.findByMemoIdOrderByModifiedAtDesc(id);
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("editcomment",new Comment());
        model.addAttribute("postcomment",new Comment());
        model.addAttribute("comment", comment);
        model.addAttribute("memo",memo);
//        comment.get(0).getUser().getUsername()
        return "detailmemo";
    }


    //게시글 수정
//    @PutMapping("/api/memos/{id}")
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
//        memoService.update(id, requestDto);
//        return id;
//    }
    @GetMapping("/detail/{id}/edit")
    public String getEditMemo(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Memo memo = memoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("memo",memo);
        return "editmemo";
    }


    @PutMapping("/detail/{id}/edit")
    public String updateMemo(@PathVariable Long id, @ModelAttribute MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        requestDto.setUser(userDetails.getUser());
        memoService.update(id, requestDto);
        return "redirect:/";

    }

    //게시글 삭제
    @DeleteMapping("/detail/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return id;
    }
}
