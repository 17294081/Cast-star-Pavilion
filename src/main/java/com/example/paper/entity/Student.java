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
 * 学生表
 * </p>
 *
 赵培辰组
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Student对象", description="学生表")
@TableName("\"student\"")
@KeySequence(value = "SEQ_BASE_STUDENT_INFO", clazz = Long.class)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "\"id\"",type = IdType.INPUT)
    private Long id;

    @TableField("\"username\"")
    @ApiModelProperty(value = "登录名称")
    private String username;

    @ApiModelProperty(value = "登录密码")
    @TableField("\"password\"")
    private String password;

    @ApiModelProperty(value = "学号")
    @TableField("\"sno\"")
    private String sno;

    @ApiModelProperty(value = "院系")
    @TableField("\"department\"")
    private String department;

    @ApiModelProperty(value = "添加时间")
    @TableField("\"addtime\"")
    private LocalDateTime addtime;

    @ApiModelProperty(value = "姓名")
    @TableField("\"name\"")
    private String name;

    @ApiModelProperty(value = "性别 1 男 2 女")
    @TableField("\"sex\"")
    private String sex;


}
