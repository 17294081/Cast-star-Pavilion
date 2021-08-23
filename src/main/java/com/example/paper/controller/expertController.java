package com.example.paper.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.paper.entity.Paper;
import com.example.paper.entity.Teacher;
import com.example.paper.service.impl.PaperServiceImpl;
import com.example.paper.service.impl.StudentServiceImpl;
import com.example.paper.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *

 */
@Controller
@RequestMapping("/expert")
public class expertController {

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
        model.addAttribute("action","/expert/login");
        return "login";
    }

    @PostMapping("/login")
    public String login_post(String username, String password, Model model){
        Teacher one = teacherService.getOne(new QueryWrapper<Teacher>().eq("\"username\"", username).eq("\"password\"", password).eq("\"role\"",2));
        if (one!=null){
            session.setAttribute("expert",one);
            return "redirect:/expert/index";
        }else{
            model.addAttribute("msg","账户或密码错误");
            model.addAttribute("action","/expert/login");
            return "login";
        }
    }

    @GetMapping(value = {"/index","/"})
    public String index(){
        if (session.getAttribute("expert")==null)
            return "redirect:/expert/login";
        return "expert/index";
    }

    @GetMapping("/paperView")
    public String paperView(Model model){
        Teacher teacher = (Teacher) session.getAttribute("expert");
        if (teacher==null)
            return "redirect:/expert/login";
        List<Paper> list = paperService.list(new QueryWrapper<Paper>().eq("\"expert\"", teacher.getId()).orderByDesc("\"type\""));
        for (Paper paper:list){
            paper.setSName(studentService.getById(paper.getSId()).getName());
            if (paper.getInstructor()!=0)
                paper.setInstructorName(teacherService.getById(paper.getExpert()).getName());
        }
        model.addAttribute("papers",list);
        return "expert/paperView";
    }
    @GetMapping("/review/{id}")
    public String review(@PathVariable("id") Long id,Model model){
        if (session.getAttribute("expert")==null)
            return "redirect:/expert/login";
        Paper paper = paperService.getById(id);
        paper.setSName(studentService.getById(paper.getSId()).getName());
        paper.setInstructorName(teacherService.getById(paper.getInstructor()).getName());
        model.addAttribute("paper",paper);
        return "expert/review";
    }
/*zpc*/
    @PostMapping("/review")
    public String review_post(Paper paper){
        if (session.getAttribute("expert")==null)
            return "redirect:/expert/login";
        paper.setReTime(LocalDateTime.now());
        boolean b = paperService.updateById(paper);
        if (b){
            return "redirect:/expert/paperView";
        }else {
            return "redirect:/expert/review/"+paper.getId();
        }
    }
    @GetMapping("/update")
    public String update(Model model){
        if (session.getAttribute("expert")==null)
            return "redirect:/expert/login";
        Teacher teacher = (Teacher) session.getAttribute("expert");
        model.addAttribute("teacher",teacher);
        return "expert/expertUpdate";
    }
    @PostMapping("/update")
    public String update_post(Teacher teacher){

        if (session.getAttribute("expert")==null)
            return "redirect:/expert/login";

        boolean b = teacherService.updateById(teacher);
        if (b){
            session.setAttribute("expert",teacher);
            return "redirect:/expert/paperView";
        }else{
            return "redirect:/expert/update";
        }

    }

    @GetMapping("/loginOut")
    public String loginOut(){
        session.removeAttribute("expert");
        return "redirect:/expert/login";
    }


}

