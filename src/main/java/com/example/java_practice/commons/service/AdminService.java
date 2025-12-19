package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.*;
import java.util.List;

public interface AdminService {

    List<Award> selAwardList(Search search); // 수상작 리스트 조회

    int cntAwardList(Search cntWorks); // 수상작 CNT

    List<WorkWithUser> selWorkWithUser(String sort, int workNo); // 작품 조회

    List<User> selUserList(String sort, int userNo); // 회원 조회(모달)

    int regWork(String sort, int workNo, String workCode); // 작품 등록

    int delWork(String sort, int workNo); // 작품 삭제

    int modInfo(List<String> work, List<String> user); // 정보 수정

    List<User> selManageList(Search userSearch); // 관리 리스트

    int cntUserList(Search cntUsers); // 회원 CNT

    int delUser(String sort, int userNo); // 회원 삭제

    int saveInfo(User user, Auth auth); // 관리 저장

    Similar compareImage(String filename); // 유사도 검색

    List<ExcelUser> selExcelList(Search userSearch); // 엑셀 행 생성
}
