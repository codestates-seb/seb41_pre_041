package seb4141preproject.members.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class MemberPatchDto {

    @ApiModelProperty(value = "회원-닉네임")
    private String name;

    @ApiModelProperty(value = "회워-패스워드")
    private String password;
}
