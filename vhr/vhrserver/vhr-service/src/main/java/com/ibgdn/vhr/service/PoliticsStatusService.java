package com.ibgdn.vhr.service;

import com.ibgdn.vhr.mapper.PoliticsstatusMapper;
import com.ibgdn.vhr.model.PoliticsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticsStatusService {

    @Autowired
    PoliticsstatusMapper politicsstatusMapper;

    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsstatusMapper.getAllPoliticsStatus();
    }
}
