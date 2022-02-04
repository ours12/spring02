package com.sparta.hwk04.repository;

import com.sparta.hwk04.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByMemoIdOrderByModifiedAtDesc(Long id);


}
