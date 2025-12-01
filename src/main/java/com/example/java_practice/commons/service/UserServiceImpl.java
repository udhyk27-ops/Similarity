package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.mapper.AwardWorkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final AwardWorkMapper awardWorkMapper;
    @Override
    public ArrayList<?> selectList(String type) {
        if(type.equals("award")){
            ArrayList<Award> awardList = awardWorkMapper.selectAwardListBySearch();
            return awardList;
        }
        return null;
    }
}
