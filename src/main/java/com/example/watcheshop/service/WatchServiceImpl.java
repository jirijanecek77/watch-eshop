package com.example.watcheshop.service;

import com.example.watcheshop.model.Watch;
import com.example.watcheshop.repository.WatchRepository;
import org.springframework.stereotype.Service;

@Service
public class WatchServiceImpl implements WatchService {

    private final WatchRepository watchRepository;

    public WatchServiceImpl(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }

    @Override
    public Watch save(Watch watch) {
        return watchRepository.save(watch);
    }

    @Override
    public boolean findDuplicity(Watch watch) {
        return !watchRepository.findByTitle(watch.getTitle()).isEmpty();
    }
}
