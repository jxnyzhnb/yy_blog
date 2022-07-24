package com.yiyu.dto.back;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @version 1.0 2022-06-01 15:55:02
 * @ClassName : com.yiyu.dto.dmin.AdminLoginDTO
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDTO {
    private String token;
    private AdminInfoDTO userInfo;

}
