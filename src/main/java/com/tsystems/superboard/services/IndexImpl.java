package com.tsystems.superboard.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tsystems.superboard.dto.StationDateDto;
import com.tsystems.superboard.dto.StationInfoDto;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class IndexImpl implements IndexInt{
    private static final Logger log = Logger.getLogger(IndexImpl.class);

    @Override
    public List<StationInfoDto> getInfo(String city) {
        List<StationInfoDto> stationInfoDtoList = new ArrayList<>();

        JSONObject requestJSON = new JSONObject();
        requestJSON.put("name", city);

        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.post("http://localhost:8060/stationInfo")
                    .header("Content-type", "application/json")
                    .body(requestJSON)
                    .asJson();
        } catch (UnirestException e){
            log.error(e);
        }

        if (response != null){
            JSONArray responseJSON = response.getBody().getArray();

            for (int i = 0; i < responseJSON.length(); i++){
                JSONObject stationInfoDtoJSON = responseJSON.getJSONObject(i);
                StationInfoDto stationInfoDto = new StationInfoDto();
                stationInfoDto.setRideId(stationInfoDtoJSON.getInt("rideId"));
                stationInfoDto.setDatetime(stationInfoDtoJSON.getString("datetime"));
                List<StationDateDto> stationDateDtoList = new ArrayList<>();

                JSONArray stationListJson = stationInfoDtoJSON.getJSONArray("stations");
                for (int j = 0; j < stationListJson.length(); j++){
                    JSONObject stationJSON = stationListJson.getJSONObject(j);
                    StationDateDto stationDateDto = new StationDateDto();
                    stationDateDto.setStation(stationJSON.getString("station"));
                    stationDateDto.setDateTime(stationJSON.getString("dateTime"));
                    stationDateDtoList.add(stationDateDto);
                }
                stationInfoDto.setStations(stationDateDtoList);
                stationInfoDtoList.add(stationInfoDto);
            }
        }
        return stationInfoDtoList;
    }
}
