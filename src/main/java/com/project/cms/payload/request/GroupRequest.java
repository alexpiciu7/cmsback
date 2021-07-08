package com.project.cms.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupRequest {
    private String groupNo;
    private int capacity;
}
