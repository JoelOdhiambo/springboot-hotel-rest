package com.example.roomwebapp.web;


import com.example.roomwebapp.RoomWebAppApplicationTests;
import com.example.roomwebapp.dto.RoomDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;


public class RoomRestControllerTest extends RoomWebAppApplicationTests {
    RoomDto roomDto;
    @Before
    public void setUp(){
        roomDto=new RoomDto();
        roomDto.setName("Manjano");
        roomDto.setNumber("M5");
        roomDto.setInfo("2Q");
    }

    @Test
    public void getRoomsWorks(){
        RestAssured.given()
                .get("/api/rooms")
                .then().log().all()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(28));
    }

    @Test
    public void addRoomWorks(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(roomDto)
                .post("/api/rooms/create")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    public void addRoomFailsInvalidNumberLength(){
        roomDto.setNumber("M53547");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(roomDto).log().all()
                .post("/api/rooms/create")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    public void addRoomFailsInvalidNameLength(){

        roomDto.setName("ManjanoManjanoManjanoManjanoManjano");


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(roomDto).log().all()
                .post("/api/rooms/create")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    public void updateRoomWorks(){
        long id = 2;
        roomDto.setNumber("A1");
        roomDto.setName("Winchester");
        roomDto.setInfo("1L");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(roomDto).log().all()
                .pathParam("id",id)
                .put("/api/rooms/update/{id}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void updateRoomFailsInvalidNumberLength(){
        long id = 2;
        roomDto.setNumber("A1A1");
        roomDto.setName("Winchester");
        roomDto.setInfo("1L");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(roomDto).log().all()
                .pathParam("id",id)
                .put("/api/rooms/update/{id}")
                .then().log().all()
                .statusCode(500);
    }

    @Test
    public void updateRoomFailsInvalidNameLength(){
        long id = 1;
        roomDto.setNumber("A1");
        roomDto.setName("WinchesterWinchesterWinchester");
        roomDto.setInfo("1L");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(roomDto).log().all()
                .pathParam("id",id)
                .put("/api/rooms/update/{id}")
                .then().log().all()
                .statusCode(500);

    }

    @Test
    public void updateRoomFailsInvalidInfoLength(){
        long id = 1;
        roomDto.setNumber("A1");
        roomDto.setName("Winchester");
        roomDto.setInfo("1LARGE");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(roomDto).log().all()
                .pathParam("id",id)
                .put("/api/rooms/update/{id}")
                .then().log().all()
                .statusCode(500);
    }

    @Test
    public void partialUpdateRoomWorks(){
        long id = 1;

        roomDto.setName("Winchester");
        roomDto.setInfo("1L");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(roomDto).log().all()
                .pathParam("id",id)
                .patch("/api/rooms/partial-update/{id}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void deleteRoomWorks(){
        long id = 1;

        RestAssured.given()
                .pathParam("id",id)
                .delete("/api/rooms/delete/{id}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void deleteRoomFailsInvalidIdType(){
        String id = "a";

        RestAssured.given()
                .pathParam("id",id)
                .delete("/api/rooms/delete/{id}")
                .then().log().all()
                .statusCode(400);
    }

}
