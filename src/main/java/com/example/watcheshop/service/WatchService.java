package com.example.watcheshop.service;

import com.example.watcheshop.model.Watch;

public interface WatchService {

    Watch save(Watch watch);

    boolean findDuplicity(Watch watch);
}
