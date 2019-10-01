<!-- wp:paragraph -->
<p>Spring Boot整合Mybatis-plus实现分页Demo 在使用Mybatis-plus之后，他相对mybatis在简单CRUD上面有很多优势，复杂的业务逻辑也可以和mybatis一样自己手动写。今天主要介绍一下mybatis-plus的分页功能。</p>
<!-- /wp:paragraph -->

<!-- wp:heading {"level":3} -->
<h3>效果预览</h3>
<!-- /wp:heading -->

<!-- wp:html -->
<img src="https://www.yremp.live/images/2019/10/01/pic0580ccd.png" alt="Spring Boot整合Mybatis-plus实现分页Demo" border="0">
<!-- /wp:html -->

<!-- wp:heading {"level":3} -->
<h3>技术栈</h3>
<!-- /wp:heading -->

<!-- wp:list {"ordered":true} -->
<ol><li>核心-Spring Boot </li><li>模板引擎-thymeleaf</li><li>数据库操作-mybatis-plus</li><li>bootstrap前端框架实现前端页面</li></ol>
<!-- /wp:list -->

<!-- wp:heading {"level":3} -->
<h3>项目相关地址</h3>
<!-- /wp:heading -->

<!-- wp:list {"ordered":true} -->
<ol><li> <a href="https://github.com/yremp/page-demo">Demo Github地址 </a></li><li><a href="https://mybatis.plus/">Mybatis-plus官方文档</a></li></ol>
<!-- /wp:list -->

<!-- wp:heading {"level":3} -->
<h3>Step 1：引入mybatis-plus和分页插件以及全部用到的依赖</h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>&lt;?xml version="1.0" encoding="UTF-8"?>
&lt;project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    &lt;modelVersion>4.0.0&lt;/modelVersion>

    &lt;groupId>live.yremp&lt;/groupId>
    &lt;artifactId>springboot_mybatis-plus_page-demo&lt;/artifactId>
    &lt;version>0.0.1-SNAPSHOT&lt;/version>
    &lt;packaging>jar&lt;/packaging>
    &lt;parent>
        &lt;groupId>org.springframework.boot&lt;/groupId>
        &lt;artifactId>spring-boot-starter-parent&lt;/artifactId>
        &lt;version>2.0.1.RELEASE&lt;/version>
        &lt;relativePath/> &lt;!-- lookup parent from repository -->
    &lt;/parent>

    &lt;properties>
        &lt;java.version>1.8&lt;/java.version>
        &lt;project.build.sourceEncoding>UTF-8&lt;/project.build.sourceEncoding>
        &lt;project.reporting.outputEncoding>UTF-8&lt;/project.reporting.outputEncoding>
        &lt;mybatisplus-spring-boot-starter.version>3.2.0&lt;/mybatisplus-spring-boot-starter.version>
        &lt;druid.version>1.1.20&lt;/druid.version>
        &lt;mysql-connector-java.version>8.0.15&lt;/mysql-connector-java.version>
    &lt;/properties>

    &lt;dependencies>
        &lt;dependency>
            &lt;groupId>org.springframework.boot&lt;/groupId>
            &lt;artifactId>spring-boot-starter-web&lt;/artifactId>
        &lt;/dependency>
        &lt;dependency>
            &lt;groupId>org.springframework.boot&lt;/groupId>
            &lt;artifactId>spring-boot-starter-logging&lt;/artifactId>
        &lt;/dependency>

        &lt;!--lombok插件使用依赖-->
        &lt;dependency>
            &lt;groupId>org.projectlombok&lt;/groupId>
            &lt;artifactId>lombok&lt;/artifactId>
            &lt;optional>true&lt;/optional>
        &lt;/dependency>
        &lt;!--mybatis plus依赖-->
        &lt;dependency>
            &lt;groupId>com.baomidou&lt;/groupId>
            &lt;artifactId>mybatis-plus-boot-starter&lt;/artifactId>
            &lt;version>${mybatisplus-spring-boot-starter.version}&lt;/version>
        &lt;/dependency>
        &lt;dependency>
            &lt;groupId>com.alibaba&lt;/groupId>
            &lt;artifactId>druid&lt;/artifactId>
            &lt;version>${druid.version}&lt;/version>
        &lt;/dependency>
        &lt;dependency>
            &lt;groupId>mysql&lt;/groupId>
            &lt;artifactId>mysql-connector-java&lt;/artifactId>
            &lt;version>${mysql-connector-java.version}&lt;/version>
        &lt;/dependency>
        &lt;dependency>
            &lt;groupId>org.springframework.boot&lt;/groupId>
            &lt;artifactId>spring-boot-starter-test&lt;/artifactId>
            &lt;scope>test&lt;/scope>
        &lt;/dependency>
        &lt;dependency>
            &lt;groupId>org.springframework.boot&lt;/groupId>
            &lt;artifactId>spring-boot-starter-thymeleaf&lt;/artifactId>
        &lt;/dependency>
    &lt;/dependencies>

&lt;/project>
</code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>Step 2 ：编写MybatisPlusConfig配置类</h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>package com.example.page_demo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("com.baomidou.cloud.service.*.mapper*")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制
         paginationInterceptor.setLimit(5);
        return paginationInterceptor;
    }
}</code></pre>
<!-- /wp:code -->

<!-- wp:paragraph -->
<p>还需要在application.properties加入以下配置</p>
<!-- /wp:paragraph -->

<!-- wp:code -->
<pre class="wp-block-code"><code>spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://yremp.live:3306/example?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf8&amp;zeroDateTimeBehavior=CONVERT_TO_NULL&amp;useSSL=false&amp;serverTimezone=CTT
spring.datasource.username=example
spring.datasource.password=123456

mybatis-plus.typeAliasesPackage=com.example.pagedemo.entity</code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>Step 3：新建User 实体类 </h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>package com.example.page_demo.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
}</code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>Step 4：新建UserMapper接口</h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>import com.example.page_demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper&lt;User> {
    IPage&lt;User> selectPage(Page page);
}</code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>Step 5：UserService</h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>package com.example.page_demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.page_demo.entity.User;
import com.example.page_demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public IPage&lt;User> selectUserPage(Page&lt;User> page) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return userMapper.selectPage(page);
    }
}</code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>Step 6：分页导航 PageDTO（实现预览中下方页码）</h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>package com.example.page_demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO {
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List&lt;Integer> pages= new ArrayList&lt;>();
    private Integer totalPage;

    public void setPagenation(Integer totalPage, Integer page) {
        if(page&lt;1) page=1;

        if(page>totalPage) page=totalPage;
        this.totalPage=totalPage;
        pages.add(page);
        this.page=page;
        for(int i=1;i&lt;=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i&lt;=totalPage){
                pages.add(page+i);
            }
        }

//        是否展示上一頁
        if(page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }

//        是否展示下一頁
        if(page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }

//        是否展示第一頁
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }

//        是否展示最後一頁
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else
        {
            showEndPage=true;
        }

    }

}</code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>Step 7：PageDtoService处理动态输入的页码</h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>package com.example.page_demo.service;

import com.example.page_demo.dto.PageDTO;
import org.springframework.stereotype.Service;

@Service
public class PageDtoService {

    public PageDTO index(Integer totalPage,Integer page) {
        PageDTO pageDto = new PageDTO();
        pageDto.setPagenation(totalPage, page);
        return pageDto;
    }
}</code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>Step  8：IndexController最终实现</h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>package com.example.page_demo.controller;

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

//     简单的体验分页 手动输入page参数
    @RequestMapping("/query/{page}")
    @ResponseBody
    public Object query(Model model,@PathVariable("page") Integer page){
        Page&lt;User> ipage = new Page&lt;>(page,5);
        IPage&lt;User> iPage = userMapper.selectPage(ipage,null);
        List&lt;User> users = iPage.getRecords();
        return users;
    }

//    详细的分页
    @RequestMapping(value = "/")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "1")Integer page)
    {
//        mybatis-plus 自带的分页功能  | page:前页码数 size：每页数据个数(需要到MybatisPlusConfig同时修改生效)
        Page&lt;User> ipage = new Page&lt;>(page,5);
//        得到Ipage
        IPage&lt;User> iPage = userMapper.selectPage(ipage,null);
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
        List&lt;User> users = iPage.getRecords();
//       返回当前页下所有数据
        model.addAttribute("users",users);
        return "index.html";
    }
}</code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>Step  9：前端html</h3>
<!-- /wp:heading -->

<!-- wp:code -->
<pre class="wp-block-code"><code>&lt;!DOCTYPE html>
&lt;html lang="en" xmlns:th="http://www.thymeleaf.org">
&lt;head>
    &lt;meta charset="UTF-8">
    &lt;title>Title&lt;/title>
    &lt;link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
&lt;/head>
&lt;body>
   &lt;div class="alert alert-success" role="alert" th:each="user : ${users}" style="margin: 20px;">
       &lt;div class="row">
           &lt;div class="col-md-1">
               &lt;img src="https://yremp.live/wp-content/uploads/2019/06/avatar.jpg"  style="height: 50px;width:50px " class="img-rounded">
           &lt;/div>
           &lt;div class="col-md-10">
               &lt;p th:text="${user.id}">&lt;/p>
               &lt;p th:text="${user.name}">&lt;/p>
           &lt;/div>
       &lt;/div>
   &lt;/div>
   &lt;nav aria-label="Page navigation" th:if="${totalPage!=0}" style="text-align: center">
       &lt;ul class="pagination">
           &lt;li th:if="${pagenation.showFirstPage}">
               &lt;a th:href="@{/(page=1)}" aria-label="Previous">
                   &lt;span aria-hidden="true">&lt;&lt;&lt;/span>
               &lt;/a>
           &lt;/li>
           &lt;li th:if="${pagenation.showPrevious}">
               &lt;a href="#" aria-label="Previous" th:href="@{/(page=${pagenation.page-1})}">
                   &lt;span aria-hidden="true">&lt;&lt;/span>
               &lt;/a>
           &lt;/li>
           &lt;li th:each="page :${pagenation.pages}" th:class="${pagenation.page==page}?'active':''">
               &lt;a href="#" aria-label="Previous" th:href="@{/(page=${page})}" th:text="${page}">
                   &lt;span aria-hidden="true">&lt;/span>
               &lt;/a>
           &lt;/li>
           &lt;li th:if="${pagenation.showNext}">
               &lt;a href="#" th:href="@{/(page=${pagenation.page+1})}" aria-label="Previous">
                   &lt;span aria-hidden="true">>&lt;/span>
               &lt;/a>
           &lt;/li>
           &lt;li th:if="${pagenation.showEndPage}">
               &lt;a href="#" th:href="@{/(page=${pagenation.totalPage})}" aria-label="Previous">
                   &lt;span aria-hidden="true">>>&lt;/span>
               &lt;/a>
           &lt;/li>
       &lt;/ul>
   &lt;/nav>
   &lt;div class="alert alert-info" role="alert" style="text-align: center">
       &lt;a href="https://yremp.live" class="alert-link">我的博客地址&lt;/a>
   &lt;/div>
&lt;/body>
&lt;/html></code></pre>
<!-- /wp:code -->

<!-- wp:heading {"level":3} -->
<h3>小结</h3>
<!-- /wp:heading -->

<!-- wp:paragraph -->
<p>以上大致上就可以实现分页功能，整个来说还是比较简单，多加练习可以熟练掌握分页功能。</p>
<!-- /wp:paragraph -->
