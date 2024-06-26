package com.javaproject.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Promotion {
    private int week;
    private int weekend;
    private String versionId;
}
