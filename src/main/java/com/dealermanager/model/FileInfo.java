package com.dealermanager.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName FileInfo
 * @Description TODO
 * @Author wujunfeng
 * @Date 2021/9/22 16:12
 * @Version 1.0
 **/
@Data
public class FileInfo {
    long id;
    @ApiModelProperty(value = "附件类型，docx,pdf等")
    String filetype;
    @ApiModelProperty(value = "附件名称")
    String filename;
    @ApiModelProperty(value = "附件关联id")
    String relateid;
    @ApiModelProperty(value = "附件目录")
    String bucketname;
    @ApiModelProperty(value = "附件所在地址")
    String objectname;
    @ApiModelProperty(value = "状态")
    int isvalid;
    @ApiModelProperty(value = "附件id")
    String fileid;
}
