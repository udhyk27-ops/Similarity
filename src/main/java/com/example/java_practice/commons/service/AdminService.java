package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.*;

import java.util.ArrayList;

public interface AdminService {

    // 수상작 리스트 조회
    ArrayList<Award> selAwardList(AwardSearch awardSearch);

    // 수상작 CNT
    int cntAwardList(AwardSearch cntWorks);

    // 작품 조회
    ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo);

    // 회원 조회(모달)
    ArrayList<User> selUserList(String sort);

    // 회원 삭제
    int delUser(int userNo);
}
