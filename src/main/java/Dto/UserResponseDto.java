package Dto;

import com.carp.carpentry.entity.Address;
import com.carp.carpentry.entity.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String username;
    private Address address;
    private Department department;

    private String test;
}
