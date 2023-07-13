//package com.example.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//public class Attendance {// davomad
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Student student;
//
//    @JsonIgnore
//    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Journal journal;
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
//}
