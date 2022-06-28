package com.example.roomwebapp.service;

import com.example.roomwebapp.dto.AddRoomDto;
import com.example.roomwebapp.dto.PartialUpdateRoomDto;
import com.example.roomwebapp.dto.RoomDto;
import com.example.roomwebapp.helper.JsonNullableUtils;
import com.example.roomwebapp.repository.RoomRepository;
import com.example.roomwebapp.entity.Room;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository){
        super();
        this.roomRepository=roomRepository;
    }


    @Override
    public List<Room> getAllRooms() {
        return this.roomRepository.findAll();
    }

    @Override
    public RoomDto createRoom(AddRoomDto addRoomDto) {
        Room room = new Room();
        room.setName(addRoomDto.getName());
        room.setNumber(addRoomDto.getNumber());
        room.setInfo(addRoomDto.getInfo());
        return roomRepository.save(room).toRoomDto();
    }


    @Override
    public RoomDto updateRoom(long id, AddRoomDto addRoomDto) {
        Room room=roomRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTITY NOT FOUND"));
        room.setName(addRoomDto.getName());
        room.setNumber(addRoomDto.getNumber());
        room.setInfo(addRoomDto.getInfo());
        return roomRepository.save(room).toRoomDto();
    }

    @Override
    public RoomDto partialRoomUpdate(long id, PartialUpdateRoomDto updateRoomDto) {

        Room room=roomRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTITY NOT FOUND"));


        JsonNullableUtils.changeIfPresent(updateRoomDto.getNumber(),room::setNumber);
        JsonNullableUtils.changeIfPresent(updateRoomDto.getName(),room::setName);
        JsonNullableUtils.changeIfPresent(updateRoomDto.getInfo(),room::setInfo);


        return roomRepository.save(room).toRoomDto();
    }


    @Override
    public void deleteRoom(long id) {
        Room room=roomRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Room " + id));
        roomRepository.delete(room);
    }

    @Override
    public RoomDto getRoomById(long id) {
        Optional<Room> result=roomRepository.findById(id);
        if (result.isPresent()){
            return result.get().toRoomDto();
        }else{
            throw new NotFoundException("Room "+ id);
        }
    }
}
