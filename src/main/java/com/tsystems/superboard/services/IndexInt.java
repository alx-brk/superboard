package com.tsystems.superboard.services;

import com.tsystems.superboard.dto.StationInfoDto;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IndexInt {
    public List<StationInfoDto> getInfo();
}
