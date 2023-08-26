package Dto;

import com.carp.carpentry.entity.Address;
import com.carp.carpentry.entity.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String username;
    private String password;
    private Address address;
    private Department department;
}
