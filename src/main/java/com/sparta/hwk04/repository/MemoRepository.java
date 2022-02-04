package com.sparta.hwk04.repository;


import com.sparta.hwk04.model.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Long> {

    Page<Memo> findAllByOrderByModifiedAtDesc(Pageable pageable);
}