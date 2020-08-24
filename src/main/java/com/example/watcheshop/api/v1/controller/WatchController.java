package com.example.watcheshop.api.v1.controller;

import com.example.watcheshop.api.v1.dto.WatchInputDTO;
import com.example.watcheshop.api.v1.dto.WatchOutputDTO;
import com.example.watcheshop.api.v1.mapper.WatchMapper;
import com.example.watcheshop.exception.AlreadyExistsException;
import com.example.watcheshop.model.Watch;
import com.example.watcheshop.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.watcheshop.api.v1.controller.ApiPath.API_WATCH;

@RestController
@RequestMapping(path = API_WATCH,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class WatchController {

    private final WatchService watchService;

    private final WatchMapper watchMapper;

    public WatchController(@Autowired WatchService watchService, @Autowired WatchMapper watchMapper) {
        this.watchService = watchService;
        this.watchMapper = watchMapper;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public WatchOutputDTO createWatch(@RequestBody @Valid WatchInputDTO inputDTO) {

        Watch watchToSave = watchMapper.mapToEntity(inputDTO);

        if (watchService.findDuplicity(watchToSave)) {
            throw new AlreadyExistsException("Watch with title " + watchToSave.getTitle() + " already exists!");
        }

        Watch watch = watchService.save(watchToSave);

        return watchMapper.mapToOutputDTO(watch);
    }
}
