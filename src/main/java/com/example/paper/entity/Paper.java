package com.example.paper.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 论文
 * </p>
 *
 赵培辰组
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Paper对象", description="论文")
@TableName("\"paper\"")
@KeySequence(value = "SEQ_BASE_PAPER_INFO", clazz = Long.class)
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "\"id\"",type = IdType.INPUT)
    private Long id;

    @TableField("\"s_id\"")
    @ApiModelProperty(value = "学生id")
    private Long sId;

    @TableField(exist = false)
    private String sName;

    @TableField("\"paper_name\"")
    @ApiModelProperty(value = "论文名称")
    private String paperName;

    @TableField("\"paper_file\"")
    @ApiModelProperty(value = "论文路径")
    private String paperFile;

    @TableField("\"source\"")
    @ApiModelProperty(value = "来源")
    private String source;

    @TableField("\"words\"")
    @ApiModelProperty(value = "字数")
    private Integer words;

    @TableField("\"field\"")
    @ApiModelProperty(value = "领域")
    private String field;

    @TableField("\"instructor\"")
    @ApiModelProperty(value = "指导老师")
    private Long instructor;

    @TableField(exist = false)
    private String instructorName;

    @TableField("\"phenomenon\"")
    @ApiModelProperty(value = "现象")
    private String phenomenon;

    @TableField("\"expert\"")
    @ApiModelProperty(value = "专家")
    private Long expert;

    @TableField(exist = false)
    private String expertName;

    @TableField("\"type\"")
    @ApiModelProperty(value = "论文状态（结论） 1待审核 2 审核通过 3 审核不通过")
    private String type;

    @ApiModelProperty(value = "上传时间")
    @TableField("\"addtime\"")
    private LocalDateTime addtime;

    @ApiModelProperty(value = "评审时间")
    @TableField("\"re_time\"")
    private LocalDateTime reTime;

}
