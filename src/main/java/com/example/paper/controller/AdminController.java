package com.example.paper.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.paper.entity.Paper;
import com.example.paper.entity.Student;
import com.example.paper.entity.Teacher;
import com.example.paper.service.impl.PaperServiceImpl;
import com.example.paper.service.impl.StudentServiceImpl;
import com.example.paper.service.impl.TeacherServiceImpl;
import com.example.paper.util.FIleUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HttpSession session;
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private PaperServiceImpl paperService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("action","/admin/login");
        return "login";
    }
    @PostMapping("/login")
    public String login_post(String username,String password,Model model){
        if ("admin".equals(username)&&"admin".equals(password)){
            session.setAttribute("admin",username);
            return "redirect:/admin/index";
        }else{
            model.addAttribute("msg","账户或密码错误");
            model.addAttribute("action","/admin/login");
            return "login";
        }
    }
    @GetMapping("/loginOut")
    public String loginOut(){
        session.removeAttribute("admin");
        return "redirect:/admin/login";
    }
    @GetMapping(value = {"/index","/"})
    public String index(){
        if (session.getAttribute("admin")==null)
            return "redirect:/admin/login";
        return "admin/index";
    }

    @GetMapping("/teacher/{role}")
    public String teacher(@PathVariable("role") String role,Model model){
        if (session.getAttribute("admin")==null)
            return "redirect:/admin/login";
        List<Teacher> list = teacherService.list(new QueryWrapper<Teacher>().eq("\"role\"", role));
        model.addAttribute("teachers",list);
        return "admin/teacherView";
    }
    @GetMapping("/student")
    public String studen(Model model){
        if (session.getAttribute("admin")==null)
            return "redirect:/admin/login";
        List<Student> list = studentService.list();
        model.addAttribute("students",list);
        return "admin/studentView";
    }
    @GetMapping("/paperView")
    public String paper(Model model){
        if (session.getAttribute("admin")==null)
            return "redirect:/admin/login";

        List<Paper> list = paperService.list();
        for (Paper paper:list){
            paper.setSName(studentService.getById(paper.getSId()).getName());
            if (paper.getInstructor()!=0)
                paper.setInstructorName(teacherService.getById(paper.getInstructor()).getName());
        }
        model.addAttribute("papers",list);
        return "admin/paperView";
    }
    @GetMapping("/teacherInsert")
    public String teacherInsert(){
        if (session.getAttribute("admin")==null)
            return "redirect:/admin/login";
        return "admin/userAdd";
    }
    @PostMapping("/teacherInsert")
    public String teacherInser_post(Teacher teacher){
        if (session.getAttribute("admin")==null)
            return "redirect:/admin/login";
        teacher.setAddtime(LocalDateTime.now());
        boolean save = teacherService.save(teacher);
        if (save)
            return "redirect:/admin/index";
        else
            return "redirect:/admin/teacherInsert";
    }
    @GetMapping("/studentInsert")
    public String studentInsert(Model model){
        if (session.getAttribute("admin")==null)
            return "redirect:/admin/login";
        model.addAttribute("action","/admin/studentInsert");
        return "common/studentInsert";
    }
    @PostMapping("/studentInsert")
    public String studentInsert_post(Student student){
        if (session.getAttribute("admin")==null)
            return "redirect:/admin/login";

        if (student.getUsername()==null||student.getPassword()==null)
            return "redirect:/admin/studentInsert";
        student.setAddtime(LocalDateTime.now());
        boolean save = studentService.save(student);
        if (save)
            return "redirect:/admin/student";
        else
            return "redirect:/admin/studentInsert";
    }


    @PostMapping("/delStu/{id}")
    @ResponseBody
    @Transient
    public Map<String,Object> del(@PathVariable("id") Long id){
        Map<String,Object> map=new HashMap<>();
        map.put("code",200);
        try {
            List<Paper> list = paperService.list(new QueryWrapper<Paper>().eq("\"s_id\"", id));
            for (Paper paper:list){
                boolean b = FIleUpload.delFile(paper.getPaperFile());
            }
            boolean remove = paperService.remove(new QueryWrapper<Paper>().eq("\"s_id\"", id));
            boolean b = studentService.removeById(id);
            if (remove&&b){
                map.put("msg","删除成功");
            }else{
                map.put("msg","删除失败");
            }
        }catch (Exception e){
            map.put("msg","删除失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return map;
    }
    @PostMapping("/delTea/{id}")
    @ResponseBody
    public Map<String,Object> delTea(@PathVariable("id") Long id){
        Map<String,Object> map=new HashMap<>();
        map.put("code",200);
        boolean b = teacherService.removeById(id);
        if (b)
            map.put("msg","删除成功");
        else
            map.put("msg","删除失败");
        return map;
    }
    @GetMapping("/look/{id}")
    public String look(@PathVariable("id") Long id,Model model){
        Paper paper = paperService.getById(id);
        if (paper.getInstructor()!=null)
            paper.setInstructorName(teacherService.getById(paper.getInstructor()).getName());
        if (paper.getExpert()!=null)
            paper.setExpertName(teacherService.getById(paper.getExpert()).getName());
        model.addAttribute("paper",paper);
        return "admin/paperLook";
    }
}
