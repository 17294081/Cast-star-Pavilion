package com.example.paper.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.paper.entity.Paper;
import com.example.paper.entity.Student;
import com.example.paper.service.impl.PaperServiceImpl;
import com.example.paper.service.impl.StudentServiceImpl;
import com.example.paper.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 学生表 前端控制器
 * </p>

 */
@Controller
@RequestMapping(value = {"/student"})
public class StudentController {

    @Autowired
    private HttpSession session;
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private PaperServiceImpl paperService;
    @Autowired
    private TeacherServiceImpl teacherService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("action","/student/login");
        return "login";
    }
    @PostMapping("/login")
    public String login_post(String username,String password,Model model){
        Student one = studentService.getOne(new QueryWrapper<Student>().eq("\"username\"", username).eq("\"password\"", password));
        if (one!=null){
            session.setAttribute("student",one);
            return "redirect:/student/index";
        }else{
            model.addAttribute("msg","账户或密码错误");
            model.addAttribute("action","/student/login");
            return "login";
        }
    }

    @GetMapping("/index")
    public String index(){
        if (session.getAttribute("student")==null)
            return "redirect:/student/login";
        return "student/index";
    }

    @GetMapping("/paperView")
    public String paperView(Model model){
        Student student = (Student) session.getAttribute("student");
        if (student==null)
            return "redirect:/student/login";
        List<Paper> list = paperService.list(new QueryWrapper<Paper>().eq("\"s_id\"", student.getId()));
        for(Paper p:list){
            if (p.getInstructor()!=0)
                p.setInstructorName(teacherService.getById(p.getInstructor()).getName());
        }
        model.addAttribute("papers",list);
        return "student/paperView";
    }

    @PostMapping("/insert")
    public String insert(Student student,String role,String url){
        if (session.getAttribute(role)==null)
            return "redirect:/"+role+"/login";

        if (student.getUsername()==null||student.getPassword()==null)
            return "redirect:/"+role+"/studentInsert";
        student.setAddtime(LocalDateTime.now());
        boolean save = studentService.save(student);
        if (save)
            return url;
        else
            return "redirect:/"+role+"/studentInsert";
    }

    @GetMapping("/update")
    public String update(Model model){
        Student student = (Student) session.getAttribute("student");
        if (student==null)
            return "redirect:/student/login";
        model.addAttribute("student",student);
        return "student/studentUpdate";
    }

    @PostMapping("/update")
    public String update_post(Student student){
        if (session.getAttribute("student")==null)
            return "redirect:/student/login";
        boolean b = studentService.updateById(student);
        if (b) {
            session.setAttribute("student",student);
            return "redirect:/student/paperView";
        }
        else
            return "redirect:/student/update";
    }
    @GetMapping("/loginOut")
    public String loginOut(){
        session.removeAttribute("student");
        return "redirect:/student/login";
    }
}

