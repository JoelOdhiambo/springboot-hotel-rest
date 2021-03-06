package com.example.roomwebapp.web;

import com.example.roomwebapp.RoomWebAppApplicationTests;
import com.example.roomwebapp.dto.EmployeeDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class EmployeeRestControllerTest extends RoomWebAppApplicationTests {
    EmployeeDto employeeDto;
    @Before
    public void setUp(){
        employeeDto=new EmployeeDto();
        employeeDto.setFirstName("Goat");
        employeeDto.setLastName("Moose");
        employeeDto.setPosition("FRONT_DESK");
    }

    @Test
    public void getEmployeesWorks(){
        RestAssured.given()
                .get("/api/employees")
                .then().log().all()
                .body("size()",greaterThanOrEqualTo(18));
    }

    @Test
    public void addEmployeeWorks(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(employeeDto)
                .post("/api/employees/create")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    public void updateEmployeeWorks(){
        String id="3cb69467-dcaa-4d53-84d9-c0d7ba439645";
        employeeDto.setFirstName("Moot");
        employeeDto.setLastName("Loot");
        employeeDto.setPosition("SECURITY");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(employeeDto).log().all()
                .pathParam("id",id)
                .put("/api/employees/update/{id}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void partialUpdateEmployeeWorks(){
        String id="3cb69467-dcaa-4d53-84d9-c0d7ba439645";
        employeeDto.setFirstName("Moose");
        employeeDto.setLastName("Toose");


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(employeeDto).log().all()
                .pathParam("id",id)
                .patch("/api/employees/partial-update/{id}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void deleteEmployeeWorks(){
        String id="3cb69467-dcaa-4d53-84d9-c0d7ba439645";

        RestAssured.given()
                .pathParam("id",id)
                .delete("/api/employees/delete/{id}")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void deleteEmployeeFailsInvalidId(){
        long id=2;

        RestAssured.given()
                .pathParam("id",id)
                .delete("/api/employees/delete/{id}")
                .then().log().all()
                .assertThat()
                .statusCode(500);
    }
}
