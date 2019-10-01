package com.example.page_demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.page_demo.dto.PageDTO;
import com.example.page_demo.entity.User;
import com.example.page_demo.mapper.UserMapper;
import com.example.page_demo.service.PageDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PageDtoService pageDtoService;

//     简单的体验分页 手动输入page
    @RequestMapping("/query/{page}")
    @ResponseBody
    public Object query(Model model,@PathVariable("page") Integer page){
        Page<User> ipage = new Page<>(page,5);
        IPage<User> iPage = userMapper.selectPage(ipage,null);
        List<User> users = iPage.getRecords();
        return users;
    }

    //    详细的分页
    @RequestMapping(value = "/")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "1")Integer page)
    {
//        mybatis-plus 自带的分页功能  | page:前页码数 size：每页数据个数(需要到MybatisPlusConfig同时修改生效)
        Page<User> ipage = new Page<>(page,5);
//        得到Ipage
        IPage<User> iPage = userMapper.selectPage(ipage,null);
//       得到总页数(Long)
        Long l_totalpages=iPage.getPages();
//       得到总页数(Integer)
        Integer totalpages=l_totalpages.intValue();
//       动态分页模型
        PageDTO pagenation = new PageDTO();
//       动态修改分页
        pagenation=pageDtoService.index(totalpages,page);
//       返回分页
        model.addAttribute("pagenation",pagenation);
//        得到当前页下所有数据
        List<User> users = iPage.getRecords();
//       返回当前页下所有数据
        model.addAttribute("users",users);
        return "index.html";
    }
}
