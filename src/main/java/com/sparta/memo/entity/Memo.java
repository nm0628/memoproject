package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 기본생성자만들어줌
public class Memo {
    private Long id;  // 메모끼리 구분을 하기 위해 넣음 : 데이터베이스에 필수로 필요한 것
    private String username;  // 메모를 작성한 사람의 이름
    private String contents;  // 그 메모에 대한 내용이 담김

    public Memo(MemoRequestDto requestDto) {
        // 클라이언트에서 받아온 데이터가 이 requestDto 에 들어 있으면
        // 그걸 사용해서 위에 Memo class - id username contents 여기에 데이터를 넣어주려는 것
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }
}