package com.example.roomwebapp.web;

import com.example.roomwebapp.entity.room.dto.AddRoomDto;
import com.example.roomwebapp.entity.room.dto.RoomDto;
import com.example.roomwebapp.entity.room.dto.UpdateRoomDto;
import com.example.roomwebapp.entity.room.model.Room;
import com.example.roomwebapp.entity.room.repository.RoomRepository;
import com.example.roomwebapp.entity.room.service.RoomService;
import com.example.roomwebapp.entity.room.service.RoomServiceImpl;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
        return ResponseEntity.ok().body(roomDto);
    }

    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody @Valid AddRoomDto addRoomDto){
        RoomDto roomDto= roomService.createRoom(addRoomDto);

        return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable("id") long id, @RequestBody AddRoomDto addRoomDto){
        RoomDto roomDto=roomService.updateRoom(id, addRoomDto);

        return ResponseEntity.ok().body(roomDto);
    }

    @PatchMapping("/partial-update/{id}")
    public  ResponseEntity<Void> partialRoomUpdate(@PathVariable("id") long id, @Valid @RequestBody UpdateRoomDto updateRoomDto){
        Optional<Room> roomOptional = Optional.of(roomRepository.getById(id));
        if (!roomOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Room room = roomOptional.get();

        if (updateRoomDto.getName().isPresent()){
            room.setName(updateRoomDto.getName().get());
        }

        if (updateRoomDto.getNumber().isPresent()){
            room.setNumber(updateRoomDto.getNumber().get());
        }

        if (updateRoomDto.getInfo().isPresent()){
            room.setInfo(updateRoomDto.getInfo().get());
        }

        roomRepository.save(room);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable long id){
        roomService.deleteRoom(id);
        ApiResponse apiResponse =new ApiResponse();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
