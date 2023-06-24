package com.fox.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName("tb_grade")

public class grade {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; //年级编号
    private String name;
    private String manager;//年级主任
    private String email;
    private String telephone;
    private String introducation;


}
