package com.feazesa.kafka.producer.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event<T> {

    private String name;
    private UUID id;
    private String version;
    private LocalDateTime timestamp;
    private String source;
    private T payload;
}
