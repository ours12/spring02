package com.sparta.hwk04.dto;

import com.sparta.hwk04.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoRequestDto {
    private String title;
    private User user;
    private String contents;
}

