package com.aston.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformSupport {
    private Long id;
    private Long gameId;
    private Long platformId;
}