package com.kokotov.hw_02_restservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformSupportDto {
    Long id;
    Long gameId;
    Long platformId;
}
