package com.dealermanager.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName BaseModel
 * @Description TODO
 * @Author wujunfeng
 * @Date 2021/9/22 17:20
 * @Version 1.0
 **/
@Data
public class BaseModel {
    @ApiModelProperty("用户id")
    String userID;
    @ApiModelProperty("页数")
    int pageNo = 1;
    @ApiModelProperty("每页条数")
    int pageSize = 20;
}
