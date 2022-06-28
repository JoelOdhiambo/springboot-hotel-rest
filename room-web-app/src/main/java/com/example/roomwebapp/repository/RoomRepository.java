package com.example.roomwebapp.repository;

import com.example.roomwebapp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository <Room,Long>{
}
