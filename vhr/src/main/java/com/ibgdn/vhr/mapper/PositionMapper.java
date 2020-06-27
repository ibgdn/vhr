package com.ibgdn.vhr.mapper;

import com.ibgdn.vhr.model.Position;
import com.ibgdn.vhr.model.ResponseBean;

import java.util.List;

public interface PositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<Position> getAllPositions();
}