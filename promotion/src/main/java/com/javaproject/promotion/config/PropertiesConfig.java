package com.javaproject.promotion.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("quantity")
@Getter
@Setter
public class PropertiesConfig {
    private int week;
    private int weekend;
    private String versionId;

}
