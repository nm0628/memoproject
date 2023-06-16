package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // 1. controller 역할을 하는 class 다라고 알려줌
                // 2. HTML 을 따로 반환하진 않기 때문에 달아줌
@RequestMapping("/api")  // api 가 중복되고 있어서 중복되는 path 를 걸어준다
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();
    // @@@@메모 생성하기 api
    @PostMapping("/memos")
    // Body 부분에 JSON 형태로 넘어올거임
    // HTTP Body 에 JSON 형태로 데이터가 넘어온다? 그럼 어떻게 받지 ? RequestBody
    // 받아오는 DTO, RequestDto 파라미터에 넣었고
    // 응답해주는 DTO, MemoResponseDto 도 반환타입으로 넣고 실제로 구현
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // RequestDto -> Entity 로 수정해야함 : 왜냐면 저장을 해야돼서
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);
        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto 로 바꿈
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;


    }
    // 조회하는  api
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map To List
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            // 해당 메모 가져오기
            Memo memo = memoList.get(id);

            // memo 수정
            memo.update(requestDto);
            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)) {
            // 해당 메모를 삭제하기
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


}
