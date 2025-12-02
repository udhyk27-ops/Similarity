package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.WorkSearch;

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
}
