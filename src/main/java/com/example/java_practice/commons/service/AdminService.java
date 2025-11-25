package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.AwardSearch;
import com.example.java_practice.commons.dto.User;

import java.util.ArrayList;

public interface AdminService {

    // 수상작 리스트 조회
    ArrayList<Award> selAwardList(AwardSearch awardSearch);

    // 수상작 CNT
    int cntAwardList(AwardSearch awardSearch);

    // 회원 조회(모달)
    ArrayList<User> selUserList(User user);
}
