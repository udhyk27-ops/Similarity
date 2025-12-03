package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.Invit;
import com.example.java_practice.commons.dto.WorkSearch;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    // 등록현황 리스트(수상작/초대작)
    ArrayList<?> selectList(String type, WorkSearch workSearch, int page, int size);
    // 등록현황 총 개수(수상작/초대작)
    int selectListTotalCnt(String type);
    int selectListSearchCnt(String type, WorkSearch workSearch);
    // 엑셀 저장
    List<?> selectListForExcel(String type, WorkSearch workSearch);
    // 발표년도 select
    List<String> selectYearList(String type);
    // 삭제
    boolean deleteWork(String type, int workNo);
    // 수상작 개별 등록
    void insertSingleAwardWork(Award award, MultipartFile file);
    // 초대작 개별 등록
    void insertSingleInvitWork(Invit invit, MultipartFile file);
    // 수상작 단체등록
    boolean insertBatchAwardWork(int userNo, List<Award> awardList, MultipartFile file);
}
