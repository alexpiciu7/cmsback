package com.project.cms.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupRequest {
    private String course_id;
    private int groupNo;
    private int capacity;
}
