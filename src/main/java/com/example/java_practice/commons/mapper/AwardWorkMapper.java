package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.WorkSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AwardWorkMapper {
    ArrayList<Award> selectAwardListBySearch(WorkSearch workSearch);
    int selectAwardListCnt();
    int selectAwardListCntBySearch(WorkSearch workSearch);
    List<String> selectAwardYearList();
}
