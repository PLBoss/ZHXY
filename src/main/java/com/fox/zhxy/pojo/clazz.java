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

@TableName("tb_clazz")

public class clazz {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id ;
    private String name ;
    private String number;
    private String introducation;
    private String headmaster;//班主任名字
    private String email;
    private String telephone;
    private String gradeName;//班级所处的年级s


}
