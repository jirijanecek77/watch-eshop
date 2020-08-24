package com.example.watcheshop.api.v1.mapper;

import com.example.watcheshop.api.v1.dto.WatchInputDTO;
import com.example.watcheshop.api.v1.dto.WatchOutputDTO;
import com.example.watcheshop.exception.InvalidWatchInputException;
import com.example.watcheshop.model.Watch;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
public class WatchMapperImpl implements WatchMapper {

    @Override
    public Watch mapToEntity(WatchInputDTO dto) {
        Watch watch = new Watch();

        watch.setId(UUID.randomUUID());
        watch.setTitle(dto.title);

        try {
            watch.setPrice(Integer.parseInt(dto.price));
        }
        catch (NumberFormatException e) {
            throw new InvalidWatchInputException(e.getMessage());
        }

        watch.setDescription(dto.description);

        try {
            watch.setFountain(Base64.getDecoder().decode(dto.fountain));
        }
        catch (IllegalArgumentException e) {
            throw new InvalidWatchInputException(e.getMessage());
        }

        return watch;
    }

    @Override
    public WatchOutputDTO mapToOutputDTO(Watch watch) {
        WatchOutputDTO dto = new WatchOutputDTO();

        dto.id = watch.getId();
        dto.title = watch.getTitle();
        dto.price = watch.getPrice();
        dto.description = watch.getDescription();

        return dto;
    }
}
