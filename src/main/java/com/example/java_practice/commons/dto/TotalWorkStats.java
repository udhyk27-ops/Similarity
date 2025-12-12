package com.example.java_practice.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TotalWorkStats {
    private WorkStats awardStats;
    private WorkStats invitStats;
}
