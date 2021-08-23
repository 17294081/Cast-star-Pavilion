package com.example.paper.service.impl;

import com.example.paper.entity.Student;
import com.example.paper.dao.StudentDao;
import com.example.paper.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 赵培辰组
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

}
