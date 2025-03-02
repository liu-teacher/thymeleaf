package com.baizhi.controller;

import com.baizhi.entity.Emp;
import com.baizhi.entity.User;
import com.baizhi.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @Value("${photo.file.dir}")
    private String realPath;

    //更新员工信息方法

    @PostMapping("/update")
    public String update(Emp emp,MultipartFile img) throws IOException {
        String originalFilename = img.getOriginalFilename();
        log.info("img name:{}",originalFilename);
        log.info("path:{}",realPath);

        boolean notEmpty = !img.isEmpty();
        System.out.println("======"+ notEmpty);
        if(notEmpty){
            String oldphoto = empService.find(emp.getId()).getPhoto();
            File file = new File(realPath,oldphoto);
            if(file.exists()){
                file.delete();
            }
            String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = fileNamePrefix+fileNameSuffix;

            img.transferTo(new File(realPath,newFileName));
            emp.setPhoto(newFileName);
        }

        empService.update(emp);
        return "redirect:/emp/findAll";
    }

    //id查询员工
    @GetMapping("/find")
    public String find(String id,Model model){
        Emp emp = empService.find(id);
        model.addAttribute("emp",emp);
        return "/ems/updateEmp";
    }

    //删除员工
    @GetMapping("/delete")
    public String delete(String id){
        String photo = empService.find(id).getPhoto();
        empService.delete(id);
        File file = new File(realPath,photo);
        if(file.exists()){
            file.delete();
        }
        return "redirect:/emp/findAll";
    }

    //保存员工
    @PostMapping("/save")
    public String save(Emp emp, MultipartFile img, HttpServletRequest request) throws IOException {
        String originalFilename = img.getOriginalFilename();
        log.info("img name:{}",originalFilename);
        log.info("path:{}",realPath);

        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = fileNamePrefix+fileNameSuffix;

        img.transferTo(new File(realPath,newFileName));

        emp.setPhoto(newFileName);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        emp.setUserid(user.getId());
        empService.save(emp);
        return "redirect:/emp/findAll";
    }

    //查询所有
    @GetMapping("/findAll")
    public String findAll(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Emp> emps = empService.findAll(user.getId());
        model.addAttribute("emps",emps);
        return "ems/emplist";
    }

}
