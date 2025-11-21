package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.AwardSearch;

import java.util.ArrayList;

public interface AdminService {

    // 수상작 리스트 조회
    ArrayList<Award> selectAwardList(AwardSearch awardSearch);

}
