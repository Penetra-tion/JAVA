package com.company;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({ "FIO", "checkPoints", "Number"})
public class Student
{

   private final String FIO;
   private final int[]checkPoints;
   private final int Number;
    @JsonCreator
    Student(@JsonProperty("FIO")String FIO,
            @JsonProperty("checkPoints") int[] checkPoints,
            @JsonProperty("Number") int Number) {
        this.FIO=FIO;
        this.checkPoints=checkPoints;
        this.Number = Number;
    }
    @JsonGetter("FIO")
    public String getFIO() {
        return FIO;
    }
    @JsonGetter("checkPoints")
    public int[] getCheckPoints() {
        return checkPoints;

    }
    @JsonGetter("Number")
    public int getNumber() {
        return Number;
    }

}
