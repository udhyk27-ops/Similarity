package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.WorkSearch;
import com.example.java_practice.commons.dto.WorkStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AwardWorkMapper {
    List<Award> selectAwardListBySearch(WorkSearch workSearch);
    int selectAwardListCnt();
    int selectAwardListCntBySearch(WorkSearch workSearch);
    List<Award> selectAwardListForExcel(WorkSearch workSearch);
    List<String> selectAwardYearList();
    int updateAwardStatusByWorkNo(@Param("workNo") int workNo);
    boolean chkDupAwardWork(@Param("f_author") String author, @Param("f_contest") String contest, @Param("f_award") String award, @Param("f_year") String year);
    void insertAwardWork(Award award);
    int selectNextWorkNo();
    int insertBatchAwardWork(List<Award> award);
    WorkStats selectAwardWorkStats();
}
