package com.sparta.hwk04.model;

import com.sparta.hwk04.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Memo memo;

    public Comment(CommentRequestDto requestDto){
        this.text = requestDto.getText();
        this.user = requestDto.getUser();
        this.memo = requestDto.getMemo();
    }
}
