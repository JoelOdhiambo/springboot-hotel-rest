package com.example.roomwebapp.entity.room.service;

import com.example.roomwebapp.entity.room.dto.AddRoomDto;
import com.example.roomwebapp.entity.room.dto.RoomDto;
import com.example.roomwebapp.entity.room.repository.RoomRepository;
import com.example.roomwebapp.entity.room.model.Room;
import org.springframework.stereotype.Service;
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
                .orElseThrow(()->new NotFoundException("Room" + id));
        room.setName(addRoomDto.getName());
        room.setNumber(addRoomDto.getNumber());
        room.setInfo(addRoomDto.getInfo());
        return roomRepository.save(room).toRoomDto();
    }

    @Override
    public Room updateRoomName(long id, String name) {
        return null;
    }

    @Override
    public Room updateBedInfo(long id, String info) {
        return null;
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
