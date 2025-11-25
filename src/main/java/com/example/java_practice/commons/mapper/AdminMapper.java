package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.AwardSearch;
import com.example.java_practice.commons.dto.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;

@Mapper
public interface AdminMapper {

    ArrayList<Award> selAwardList(AwardSearch awardSearch);

    int cntAwardList(AwardSearch awardSearch);

    ArrayList<User> selUserList(User user);
}
