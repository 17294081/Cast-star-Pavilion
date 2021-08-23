package com.example.paper.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.paper.entity.Paper;
import com.example.paper.entity.Student;
import com.example.paper.entity.Teacher;
import com.example.paper.service.impl.PaperServiceImpl;
import com.example.paper.service.impl.StudentServiceImpl;
import com.example.paper.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private HttpSession session;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private PaperServiceImpl paperService;
    @Autowired
    private StudentServiceImpl studentService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("action","/teacher/login");
        return "login";
    }

    @PostMapping("/login")
    public String login_post(String username, String password, Model model){
        Teacher one = teacherService.getOne(new QueryWrapper<Teacher>().eq("\"username\"", username).eq("\"password\"", password).eq("\"role\"",1));
        if (one!=null){
            session.setAttribute("teacher",one);
            return "redirect:/teacher/index";
        }else{
            model.addAttribute("msg","账户或密码错误");
            model.addAttribute("action","/teacher/login");
            return "login";
        }
    }

    @GetMapping(value = {"/index","/"})
    public String index(){
        if (session.getAttribute("teacher")==null)
            return "redirect:/teacher/login";
        return "teacher/index";
    }
    @GetMapping("/paperView")
    public String paperView(Model model){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher==null)
            return "redirect:/teacher/login";
        List<Paper> list = paperService.list(new QueryWrapper<Paper>().eq("\"instructor\"", teacher.getId()).orderByDesc("\"type\""));
        for (Paper paper:list){
            paper.setSName(studentService.getById(paper.getSId()).getName());
            if (paper.getExpert()!=null)
                paper.setExpertName(teacherService.getById(paper.getExpert()).getName());
        }
        model.addAttribute("papers",list);
        return "teacher/paperView";
    }
    @GetMapping("/review/{id}")
    public String review(@PathVariable("id") Long id,Model model){
        if (session.getAttribute("teacher")==null)
            return "redirect:/teacher/login";
        Paper paper = paperService.getById(id);
        paper.setSName(studentService.getById(paper.getSId()).getName());

        List<Teacher> teachers = teacherService.list(new QueryWrapper<Teacher>().eq("\"role\"", "2"));
        model.addAttribute("teachers",teachers);
        model.addAttribute("paper",paper);
        return "teacher/review";
    }

    @PostMapping("/review")
    public String review_post(Paper paper){
        if (session.getAttribute("teacher")==null)
            return "redirect:/teacher/login";
        if (!"无".equals(paper.getPhenomenon())){
            paper.setType("3");
            paper.setExpert(null);
        }else
            paper.setType("4");
        boolean b = paperService.updateById(paper);
        if (b){
            return "redirect:/teacher/paperView";
        }else {
            return "redirect:/teacher/review/"+paper.getId();
        }
    }
    @GetMapping("/update")
    public String update(Model model){
        if (session.getAttribute("teacher")==null)
            return "redirect:/teacher/login";
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        model.addAttribute("teacher",teacher);
        return "teacher/teacherUpdate";
    }
    @PostMapping("/update")
    public String update_post(Teacher teacher){

        if (session.getAttribute("teacher")==null)
            return "redirect:/teacher/login";

        boolean b = teacherService.updateById(teacher);
        if (b){
            session.setAttribute("teacher",teacher);
            return "redirect:/teacher/paperView";
        }else{
            return "redirect:/teacher/update";
        }

    }

    @GetMapping("/loginOut")
    public String loginOut(){
        session.removeAttribute("teacher");
        return "redirect:/teacher/login";
    }

    @GetMapping("/look/{id}")
    public String look(@PathVariable("id") Long id,Model model){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher==null)
            return "redirect:/teacher/login";
        Paper paper = paperService.getById(id);
        if (paper.getInstructor()!=null)
            paper.setInstructorName(teacherService.getById(paper.getInstructor()).getName());
        if (paper.getExpert()!=null)
            paper.setExpertName(teacherService.getById(paper.getExpert()).getName());
        model.addAttribute("paper",paper);
        return "teacher/paperLook";
    }

}

