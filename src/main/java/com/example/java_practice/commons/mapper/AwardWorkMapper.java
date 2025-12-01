package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface AwardWorkMapper {
    ArrayList<Award> selectAwardListBySearch();

}
