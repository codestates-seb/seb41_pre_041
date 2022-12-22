package seb4141preproject.members.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResponseDto {
    private long id;

    private String name;

    private String email;

    private String memberToken;


}
