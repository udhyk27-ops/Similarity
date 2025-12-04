package com.example.java_practice.commons.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalWorkStats {
    private WorkStats awardStats;
    private WorkStats invitStats;
}
