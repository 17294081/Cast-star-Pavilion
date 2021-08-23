package com.example.paper.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.paper.entity.Paper;
import com.example.paper.entity.Student;
import com.example.paper.entity.Teacher;
import com.example.paper.service.impl.PaperServiceImpl;
import com.example.paper.service.impl.TeacherServiceImpl;
import com.example.paper.util.FIleUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 论文 前端控制器
 * 赵培辰
 * </p>
 *

 */
@Controller
@RequestMapping("/paper")
public class PaperController {

    private static final String path = File.separator+"file"+File.separator+"paper";

    @Autowired
    private HttpSession session;
    @Autowired
    private PaperServiceImpl paperService;
    @Autowired
    private TeacherServiceImpl teacherService;

    @GetMapping("/insert")
    public String insert(Model model){
        if (session.getAttribute("student")==null){
            return "redirect:/student/login";
        }
        List<Teacher> list = teacherService.list(new QueryWrapper<Teacher>().eq("\"role\"", 1));
        model.addAttribute("teachers",list);
        return "student/paperInsert";
    }
    @PostMapping("/insert")
    public String insert_post(Paper paper, @RequestParam("file") MultipartFile file){
        Student student = (Student) session.getAttribute("student");
        if (student==null){
            return "redirect:/student/login";
        }
        String fileName = file.getOriginalFilename();
        if (fileName!=null&&fileName.contains(".pdf")){
            fileName = FIleUpload.upload(file, path);
            paper.setPaperFile(path+File.separator+fileName);
            paper.setSId(student.getId());
            paper.setAddtime(LocalDateTime.now());
            paper.setType("1");
            boolean save = paperService.save(paper);
            if (save){
                return "redirect:/student/paperView";
            }else {
                return "student/paperInsert";
            }
        }
        return "student/paperInsert";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,Model model){
        Student student = (Student) session.getAttribute("student");
        Paper paper = paperService.getById(id);
        if (paper.getInstructor()!=0)
            paper.setInstructorName(teacherService.getById(paper.getInstructor()).getName());

        List<Teacher> teachers = teacherService.list(new QueryWrapper<Teacher>().eq("\"role\"", "1"));
        model.addAttribute("paper",paper);
        model.addAttribute("teachers",teachers);
        return "student/paperUpdate";
    /*zpc*/
    }
    @PostMapping("/update")
    public String update_post(Paper paper, @RequestParam("file") MultipartFile file, Model model){
        Student student = (Student) session.getAttribute("student");
        if (student==null){
            return "redirect:/student/login";
        }
        String fileName = file.getOriginalFilename();
        if (fileName!=null&&fileName.contains(".pdf")){
            fileName = FIleUpload.upload(file, path);
            paper.setPaperFile(path+File.separator+fileName);
            paper.setSId(student.getId());
            paper.setAddtime(LocalDateTime.now());
            boolean save = paperService.updateById(paper);
            if (save){
                return "redirect:/student/paperView";
            }else {
                return "student/paperUpdate";
            }
        }
        return "student/paperUpdate";
    }

    @GetMapping("/look/{id}")
    public String look(@PathVariable("id") Long id,Model model){
        Paper paper = paperService.getById(id);
        if (paper.getInstructor()!=null)
            paper.setInstructorName(teacherService.getById(paper.getInstructor()).getName());
        if (paper.getExpert()!=null)
            paper.setExpertName(teacherService.getById(paper.getExpert()).getName());
        model.addAttribute("paper",paper);
        return "student/paperLook";
    }

}

