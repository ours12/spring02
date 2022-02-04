package com.sparta.hwk04.dto;

import com.sparta.hwk04.model.Memo;
import com.sparta.hwk04.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String text;
    private User user;
    private Memo memo;
}
