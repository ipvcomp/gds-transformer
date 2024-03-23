package com.ipurvey.gdstransformerservice.amadeus.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class ContactInfoDto {
    @Indexed
    private String email;
    private String phone;
}
