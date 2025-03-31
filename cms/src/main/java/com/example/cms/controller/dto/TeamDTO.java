package com.example.cms.controller.dto;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private String teamID;
    private String teamName;
    private CaptainDTO captain;


}

