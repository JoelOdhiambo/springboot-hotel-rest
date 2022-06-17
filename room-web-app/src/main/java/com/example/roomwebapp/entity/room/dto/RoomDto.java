package com.example.roomwebapp.entity.room.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class RoomDto {

    private long id;
    @Size(max = 2)
    private String number;
    @Size(max = 16)
    private String name;
    @Size(max = 2)
    private String info;

}
