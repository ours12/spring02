package com.sparta.hwk04.service;


import com.sparta.hwk04.dto.MemoRequestDto;
import com.sparta.hwk04.model.Memo;
import com.sparta.hwk04.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    @Transactional
    public Long update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        memo.update(requestDto);
        return memo.getId();
    }

//    public Memo createMemo(MemoRequestDto requestDto, Long userId) {
//        Memo memo = new Memo(requestDto, userId);
//
//       memoRepository.save(memo);
//
//        return memo;
//    }

//    // 회원 ID 로 등록된 상품 조회
//    public List<Memo> getMemo(Long userId) {
//        return memoRepository.findAllByUserId(userId);
//    }
}


