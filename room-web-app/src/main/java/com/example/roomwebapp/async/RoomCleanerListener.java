package com.example.roomwebapp.async;

import com.example.roomwebapp.dto.RoomDto;
import com.example.roomwebapp.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

@Component
public class RoomCleanerListener {
    private static final Logger LOG= LoggerFactory.getLogger(RoomCleanerListener.class);

    private final ObjectMapper mapper;
    private final RoomService roomService;

    public RoomCleanerListener(ObjectMapper mapper, RoomService roomService) {
        this.mapper = mapper;
        this.roomService = roomService;
    }


    public void receiveMessage(String message){
        try{
            AsyncPayload payload=mapper.readValue(message,AsyncPayload.class);
            if("ROOM".equals(payload.getModel())){
                RoomDto roomDto=roomService.getRoomById(payload.getId());
                LOG.info("ROOM {}:{} needs to be cleaned", roomDto.getNumber(),roomDto.getName());
            }else{
                LOG.warn("Unknown model type");
            }
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
