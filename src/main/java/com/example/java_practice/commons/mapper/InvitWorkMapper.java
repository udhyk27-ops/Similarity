package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Invit;
import com.example.java_practice.commons.dto.WorkSearch;
import com.example.java_practice.commons.dto.WorkStats;
import com.example.java_practice.commons.dto.WorkYearStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvitWorkMapper {
    List<Invit> selectInvitListBySearch(WorkSearch workSearch);
    int selectInvitListCnt();
    int selectInvitListCntBySearch(WorkSearch workSearch);
    List<Invit> selectInvitListForExcel(WorkSearch workSearch);
    List<String> selectInvitYearList();
    int updateInvitStatusByWorkNo(@Param("workNo") int workNo);
    boolean chkDupInvitWork(@Param("f_title") String title, @Param("f_author") String author, @Param("f_year") String year);
    void insertInvitWork(Invit invit);
    int selectNextWorkNo();
    int insertBatchInvitWork(List<Invit> invit);
    WorkStats selectInvitWorkStats();
    List<WorkYearStats> selectInvitYearStats();
}
