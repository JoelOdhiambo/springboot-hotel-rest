package com.example.roomwebapp.entity;

import com.example.roomwebapp.dto.RoomDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Room")
@Getter
@Setter
public class Room {
    @Id
    @Column(name = "ROOM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "ROOM_NUMBER")
    private String number;
    @Column(name = "NAME")
    private String name;
    @Column(name = "BED_INFO")
    private String info;

    public Room() {
    }

    public Room(long id, String number, String name, String info) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.info = info;
    }

    public RoomDto toRoomDto(){
        RoomDto roomDto=new RoomDto();
        roomDto.setId(this.id);
        roomDto.setName(this.name);
        roomDto.setInfo(this.info);
        roomDto.setNumber(this.number);

        return  roomDto;

    }
}
