package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.*;

import java.util.ArrayList;

public interface AdminService {

    // 수상작 리스트 조회
    ArrayList<Award> selAwardList(Search search);

    // 수상작 CNT
    int cntAwardList(Search cntWorks);

    // 작품 조회
    ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo);

    // 회원 조회(모달)
    ArrayList<User> selUserList(String sort, Integer userNo);

    // 작품 삭제
    int delWork(String sort, int workNo);

    // 정보 수정
    int modInfo(ArrayList<String> work, ArrayList<String> user);

    // 관리 리스트
    ArrayList<User> selManageList(Search userSearch);

    // 회원 CNT
    int cntUserList(Search cntUsers);

    // 회원 삭제
    int delUser(String sort, int userNo);
}
