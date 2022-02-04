package com.sparta.hwk04.controller;

import com.sparta.hwk04.dto.CommentRequestDto;
import com.sparta.hwk04.model.Memo;
import com.sparta.hwk04.model.Comment;
import com.sparta.hwk04.repository.MemoRepository;
import com.sparta.hwk04.repository.CommentRepository;
import com.sparta.hwk04.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemoRepository memoRepository;

    @PostMapping("/detail/{id}/comment")
    public String createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = new Comment(requestDto);
        Memo memo = memoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        comment.setUser(userDetails.getUser());
        comment.setMemo(memo);
        commentRepository.save(comment);
        return "redirect:/detail/{id}";
    }

    @PutMapping("/detail/{id}/comment/{commentId}")
    public String editComment(@PathVariable Long commentId, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        comment.setText(requestDto.getText());

        commentRepository.save(comment);
        return "redirect:/detail/{id}";
    }

    @DeleteMapping("/detail/{id}/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
        return "redirect:/detail/{id}";
    }
}