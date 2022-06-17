package com.example.roomwebapp.entity.room.repository;

import com.example.roomwebapp.entity.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository <Room,Long>{
}
