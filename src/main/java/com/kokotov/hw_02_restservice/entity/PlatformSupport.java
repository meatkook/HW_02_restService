package com.kokotov.hw_02_restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformSupport {
    Long id;
    Long gameId;
    Long platformId;
}
