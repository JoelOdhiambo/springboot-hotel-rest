package com.example.roomwebapp.web;

import com.example.roomwebapp.dto.AddRoomDto;
import com.example.roomwebapp.dto.RoomDto;
import com.example.roomwebapp.dto.PartialUpdateRoomDto;
import com.example.roomwebapp.entity.Room;
import com.example.roomwebapp.repository.RoomRepository;
import com.example.roomwebapp.service.RoomService;
import com.example.roomwebapp.service.RoomServiceImpl;
import com.example.roomwebapp.helper.JsonNullableUtils;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;

    public RoomRestController(RoomServiceImpl roomService, RoomRepository roomRepository) {
        super();
        this.roomService = roomService;
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public List<RoomDto>getAllRooms(){
        return roomService.getAllRooms().stream().map(Room::toRoomDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomId(@PathVariable(name="id")Long id){
        RoomDto roomDto=roomService.getRoomById(id);
        return new ResponseEntity<>(roomDto,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody @Valid AddRoomDto addRoomDto){
        RoomDto roomDto= roomService.createRoom(addRoomDto);

        return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable("id") long id, @RequestBody AddRoomDto addRoomDto){
        RoomDto roomDto=roomService.updateRoom(id, addRoomDto);

        return new ResponseEntity<>(roomDto,HttpStatus.OK);
    }

    @PatchMapping("/partial-update/{id}")
    public  ResponseEntity<RoomDto> partialRoomUpdate(@PathVariable("id") long id,@RequestBody PartialUpdateRoomDto updateRoomDto){

        RoomDto roomDto = roomService.partialRoomUpdate(id, updateRoomDto);

        return new ResponseEntity<>(roomDto,HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>("Room "+ id + " deleted successfully!",HttpStatus.OK);
    }

}
