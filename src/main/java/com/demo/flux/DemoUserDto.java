package com.demo.flux;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DemoUserDto {

    private String userId;
    private String userName;

}
