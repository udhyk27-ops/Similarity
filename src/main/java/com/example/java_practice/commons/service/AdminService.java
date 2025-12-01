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

    // 작품 삭제
    int delWork(String sort, int workNo);

    // 정보 수정
    int modInfo(ArrayList<String> work, ArrayList<String> user);

    // 회원 CNT
    int cntUserList(AwardSearch cntUsers);
}
