package com.example.roomwebapp.service;

import com.example.roomwebapp.dto.AddRoomDto;
import com.example.roomwebapp.dto.PartialUpdateRoomDto;
import com.example.roomwebapp.dto.RoomDto;
import com.example.roomwebapp.entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    RoomDto createRoom(AddRoomDto addRoomDto);
    RoomDto updateRoom(long id,AddRoomDto addRoomDto);
    RoomDto partialRoomUpdate(long id, PartialUpdateRoomDto updateRoomDto);
    void deleteRoom(long id);
    RoomDto getRoomById(long id);
}
