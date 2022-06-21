package com.example.roomwebapp.entity.room.dto;

import lombok.Getter;
import lombok.Setter;

import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuppressWarnings("FieldMayBeFinal")
public class PartialUpdateRoomDto {
    @NotNull
    @Size(max = 2)
    private JsonNullable<String> number=JsonNullable.undefined();
    @NotNull
    @Size(max = 16)
    private JsonNullable<String>  name=JsonNullable.undefined();
    @NotNull
    @Size(max = 2)
    private JsonNullable<String>  info=JsonNullable.undefined();

    protected PartialUpdateRoomDto(){}

}
