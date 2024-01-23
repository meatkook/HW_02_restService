package com.aston.restservice.dto;

import lombok.Data;

@Data
public class PlatformSupportDto {
    private Long id;
    private Long gameId;
    private Long platformId;
}