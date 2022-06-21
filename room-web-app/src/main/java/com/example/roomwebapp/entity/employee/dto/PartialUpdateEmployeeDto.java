package com.example.roomwebapp.entity.employee.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuppressWarnings("FieldMayBeFinal")
public class PartialUpdateEmployeeDto {

    @NotNull
    @Size(max = 64)
    private JsonNullable <String> firstName=JsonNullable.undefined();
    @NotNull
    @Size(max = 64)
    private JsonNullable <String> lastName=JsonNullable.undefined();
    @NotNull
    @Size(max = 64)
    private JsonNullable <String> position=JsonNullable.undefined();

    protected  PartialUpdateEmployeeDto(){}
}
