package com.example.roomwebapp.entity.room.service;

import com.example.roomwebapp.entity.room.dto.AddRoomDto;
import com.example.roomwebapp.entity.room.dto.RoomDto;
import com.example.roomwebapp.entity.room.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    RoomDto createRoom(AddRoomDto addRoomDto);
    RoomDto updateRoom(long id,AddRoomDto addRoomDto);
    Room updateRoomName(long id, String name);
    Room updateBedInfo(long id, String info);
    void deleteRoom(long id);
    RoomDto getRoomById(long id);
}
