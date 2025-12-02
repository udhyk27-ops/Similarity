package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.Invit;
import com.example.java_practice.commons.dto.WorkSearch;
import com.example.java_practice.commons.mapper.AwardWorkMapper;
import com.example.java_practice.commons.mapper.InvitWorkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final AwardWorkMapper awardWorkMapper;
    private final InvitWorkMapper invitWorkMapper;
    @Override
    public ArrayList<?> selectList(String type, WorkSearch workSearch, int page, int size) {
        int startRow = (page - 1) * size + 1;
        int endRow = page * size;

        workSearch.setStartRow(startRow);
        workSearch.setEndRow(endRow);
        if(type.equals("award")){
            ArrayList<Award> awardList = awardWorkMapper.selectAwardListBySearch(workSearch);
            return awardList;
        }else{
            ArrayList<Invit> invitList = invitWorkMapper.selectInvitListBySearch(workSearch);
            return invitList;
        }
    }

     @Override
    public int selectListTotalCnt(String type) {
        if(type.equals("award")){
            return awardWorkMapper.selectAwardListCnt();
        }else{
            return invitWorkMapper.selectInvitListCnt();
        }
    }

    @Override
    public int selectListSearchCnt(String type, WorkSearch workSearch) {
        if(type.equals("award")){
            return awardWorkMapper.selectAwardListCntBySearch(workSearch);
        }else{
            return invitWorkMapper.selectInvitListCntBySearch(workSearch);
        }
    }

    @Override
    public List<String> selectYearList(String type) {
        if(type.equals("award")){
            return awardWorkMapper.selectAwardYearList();
        }else{
            return invitWorkMapper.selectInvitYearList();
        }
    }
}
