package kr.co.jhta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.jhta.vo.User;

// @RestController 어노테이션은 데이터를 내려보내는 응답을 보낼 때 사용한다. 객체를 제이슨 형식으로 응답한다. - (ajax 비동기 처리)
// @Controller 어노테이션은 view(.jsp)를 내려보내는 응답을 보낼 때 사용한다. jsp의 이름을 응답한다.
@Controller
public class HomeController {
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
}
