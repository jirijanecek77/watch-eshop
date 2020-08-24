package com.example.watcheshop.api.v1.mapper;

import com.example.watcheshop.api.v1.dto.WatchInputDTO;
import com.example.watcheshop.api.v1.dto.WatchOutputDTO;
import com.example.watcheshop.model.Watch;

public interface WatchMapper {

    Watch mapToEntity(WatchInputDTO dto);

    WatchOutputDTO mapToOutputDTO(Watch watch);
}
