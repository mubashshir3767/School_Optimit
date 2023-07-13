//package com.example.model.response;
//
//import com.example.entity.Attendance;
//import com.example.entity.Student;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import lombok.*;
//
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//
//public class AttendanceResponse {// davomad
//
//    private UUID id;
//
//    private Integer studentId;
//    private String studentName;
//    private String studentSurname;
//
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    private LocalDateTime createdDate;
//
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    private LocalDateTime updateDate;
//
//    private boolean come;
//
//    public static AttendanceResponse from(Attendance attendance){
//        return AttendanceResponse.builder()
//                .id(attendance.getId())
//                .createdDate(attendance.getCreatedDate())
//                .updateDate(attendance.getUpdateDate())
//                .come(attendance.isCome())
//                .studentId(attendance.getStudent().getId())
//                .studentSurname(attendance.getStudent().getLastName())
//                .studentName(attendance.getStudent().getFirstName())
//                .build();
//    }
//}
