package com.nwm.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.io.IOUtils;

@Controller
public class CustomOpenApiUiController {

    @Value("classpath:swagger-ui.css")
    private Resource cssFile;

    // đọc file css mặc định khi load /swagger-ui/index.html#
    // sau đó set lại bằng file css đã chỉnh và response về cho trang index.html của swagger
    @GetMapping(value = "/swagger-ui/swagger-ui.css")
    public void resourceCSS(HttpServletRequest request, HttpServletResponse response) {
        setResource(cssFile, response, "text/css;charset=UTF-8");
    }

    private void setResource(Resource resource, HttpServletResponse response, String contentType) {
        String str = null;
        try {
            response.setHeader("content-type", contentType);
            response.setHeader("Pragma", "no-cache");
            byte[] file = IOUtils.toByteArray(Objects.requireNonNull(resource.getURI()));
            response.getOutputStream().write(file);
        } catch (Exception e) {
            str = e.getMessage();
        }
    }
}
