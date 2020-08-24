package com.example.watcheshop.api.v1.controller;

import com.example.watcheshop.api.v1.dto.WatchInputDTO;
import com.example.watcheshop.api.v1.dto.WatchOutputDTO;
import com.example.watcheshop.api.v1.mapper.WatchMapper;
import com.example.watcheshop.model.Watch;
import com.example.watcheshop.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.watcheshop.api.v1.controller.ApiPath.API_WATCH;

@RestController
@RequestMapping(API_WATCH)
public class WatchController {

    private final WatchService watchService;

    private final WatchMapper watchMapper;

    public WatchController(@Autowired WatchService watchService, @Autowired WatchMapper watchMapper) {
        this.watchService = watchService;
        this.watchMapper = watchMapper;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public WatchOutputDTO createWatch(@RequestBody WatchInputDTO inputDTO) {

        Watch watch = watchService.save(watchMapper.mapToEntity(inputDTO));

        return watchMapper.mapToOutputDTO(watch);
    }
}
