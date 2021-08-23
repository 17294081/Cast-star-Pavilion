package com.example.paper.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
赵培辰组
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Teacher对象", description="")
@TableName("\"teacher\"")
@KeySequence(value = "SEQ_BASE_TEACHER_INFO", clazz = Long.class)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "\"id\"",type = IdType.INPUT)
    private Long id;

    @TableField("\"username\"")
    @ApiModelProperty(value = "账户名")
    private String username;

    @TableField("\"password\"")
    @ApiModelProperty(value = "密码")
    private String password;

    @TableField("\"role\"")
    @ApiModelProperty(value = "角色 1指导老师 2 专家")
    private String role;

    @TableField("\"name\"")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField("\"department\"")
    @ApiModelProperty(value = "院系")
    private String department=null;

    @TableField("\"sex\"")
    @ApiModelProperty(value = "性别 1 男 2 女")
    private String sex=null;

    @TableField("\"addtime\"")
    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addtime;


}
