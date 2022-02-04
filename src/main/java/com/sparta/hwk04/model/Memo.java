package com.sparta.hwk04.model;


import com.sparta.hwk04.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Setter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Memo extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

//    @Column(nullable = false)
//    private Long userId;

    @ManyToOne
    @JoinColumn(nullable=false)
    private User user;

    @OneToMany(mappedBy = "memo",cascade = CascadeType.REMOVE)
    List<Comment> comment = new ArrayList<>();



//메모 생성 시 이용합니다
    public Memo(MemoRequestDto requestDto) {
        //메모를 등록한 회원 테이블 Id 저장
//        this.userId = userId;
        this.title = requestDto.getTitle();
        this.user = requestDto.getUser();
        this.contents = requestDto.getContents();
    }

    public void update(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.user = requestDto.getUser();
        this.contents = requestDto.getContents();
    }
}

