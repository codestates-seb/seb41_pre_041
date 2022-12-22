package seb4141preproject.members.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private long id;

    private String name;

    private String email;

    private String password;

    private List<String> roles;
}
