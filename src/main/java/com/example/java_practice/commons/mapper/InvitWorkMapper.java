package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Invit;
import com.example.java_practice.commons.dto.WorkSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface InvitWorkMapper {
    ArrayList<Invit> selectInvitListBySearch(WorkSearch workSearch);
    int selectInvitListCnt();
    int selectInvitListCntBySearch(WorkSearch workSearch);
    List<String> selectInvitYearList();
}
