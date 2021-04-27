package com.project.cms.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Duration {
    private Date startDate;
    private Date endDate;

}
