package spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 스프링에서 MVC 모델 지원
@Controller // 스프링 MVC 패턴 내 해당클래스를 Controller
public class IndexController {

    @GetMapping("/") // Http URL 설정
    public String index() {

        return "index";

    }

}
