package kr.co.jhta.security;



import java.security.interfaces.RSAKey;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
/*
 * @Configuration
 * 		- 이 어노테이션이 지정된 클래스는 스프링 빈 설정 클래스로 사용된다.
 * 		- 이 어노테이션이 지정된 클래스는 컴포넌트 스캔의 대상이 된다.
 * 		- 클래스 안에서 @Bean 어노테이션이 적용된 메소드가 반환하는 객체가 
 * 		  스프링 컨테이너의 빈으로 등록된다.
 * 		- 예시
 * 			// 아래의 메소드가 반환하는 BCryptPasswordEncoder객체를 스프링 컨테이너에 등록시킨다.
 * 			@Bean
 * 			public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
			}
			
			// 위의 자바코드는 아래의 빈 설정과 동일한 효과가 발휘된다.
			<bean id="passwordEncoder" class="BCryptPasswordEncoder" />  

 * @EnableWebSecurity
 * 		- 이 어노테이션은 Spring Security 기능을 활성화하고, Spring Security 관련 설정을
 * 		  자동으로 구성한다.
 */

/* @EnableGlobalMethodSecurity(securedEnabled = true)
 * 		- Spring security에서 애플리케이션 내부의 메소드에 접근제어설정(보안설정)을 할 수 있게 한다.
 * 		- @PreAuthorize, @PrePostEnabled, @Secured 어노테이션을 사용해서 메소드별로 접근제어를 설정한다. 
 * 		- @EnableGlobalMethodSecurity에서 prePostEnabled(@PreAuthorize, @PrePostEnabled 허용 여부)와 securedEnabled(@Secured 허용 여부) 속성을 설정해서 
 * 		  @PreAuthorize, @PrePostEnabled, @Secured 중에서 어느 어노테이션을 사용할지 설정한다.
 * 		- 접근제어와 관련된 설정이 없는 메소드는 익명사용자도 접근할 수 있게 된다.
 * 		- 설정방법
 * 				먼저 실행됨
 * 				@PreAuthorize와 @PostAuthorize
 * 					@PreAuthorize("isAuthenticated()")
 * 					@PreAuthorize("hasRole('ROLE_USER')")
 * 					@PreAuthorize("hasRole('ROLE_USER', 'ROLE_ADMIN')")
 * 					@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
 * 				
 * 				@Secured
 * 					@Secured("ROLE_USER")
 * 					@Secured({"ROLE_USER", "ROLE_ADMIN"})
 * 				두 개 다 설정 위가 더 강력
 * 
 * 		 - 설정 예시
 * 			1. 컨트롤러의 요청핸들러 메소드에 @Secured 어노테이션 설정하기
 * 				+ 해당 요청핸들러 메소드에 지정된 권한을 보유하고 있는 사용자만 그 요청핸들러 메소드의 실행을 URL로 요청할 수 있다.
 * 			@Controller
 * 			@RequestMapping("/users")
 * 			public class UserController {
 * 				
 * 				// 익명사용자도 요청할 수 있다.
 * 				@GetMapping("/")
 * 				public String home() {...}
 * 
 * 				// "ROLE_USER"권한을 보유한 인증된 사용자만 요청할 수 있다.
 * 				@Secured("ROLE_USER")
 * 				@GetMapping("/info")
 * 				public String myInfo() {...}
 * 
				// "ROLE_USER", "ROLE_ADMIN" 권한을 모두 보유한 인증된 사용자만 요청할 수 있다.
 * 				@Secured({"ROLE_USER", "ROLE_ADMIN"})
 * 				@GetMapping("/info")
 * 				public String myInfo() {...}
 * 			
 * 			}
 * 
 * 			2. 컨트롤러 클래스에 @Secured 어노테이션 설정하기 - 유저권한 있어야 함
 * 				+ 해당 컨트롤러 클래스의 모든 요청핸들러 메소드는 지정된 권한을 보유하고 있는
 * 				  사용자만 접근할 수 있다.
 * 
 * 			@Controller
 * 			@RequestMapping("/users")
 * 			@Secured("ROLE_USER")
 * 			public class UserController {
 * 
 * 				// "ROLE_USER"권한을 보유한 인증된 사용자만 요청할 수 있다.
 * 				@GetMapping("/")
 * 				public String home() {...}
 * 
 * 				// "ROLE_USER"권한을 보유한 인증된 사용자만 요청할 수 있다.
 * 				@GetMapping("/info")
 * 				public String myInfo() {...}
 * 			
 * 			}
 * 
 * 			3. 컨트롤러 클래스와 요청핸들러 메소드에 @Secured 어노테이션 같이 설정하기
 * 
 * 			@Controller
 * 			@RequestMapping("/users")
 * 			@Secured("ROLE_USER")
 * 			public class UserController {
 * 
 * 				// "ROLE_USER"권한을 보유한 인증된 사용자만 요청할 수 있다.
 * 				@GetMapping("/")
 * 				public String home() {...}
 * 
 * 				// "ROLE_USER"권한을 보유한 인증된 사용자만 요청할 수 있다.
 * 				@GetMapping("/info")
 * 				public String myInfo() {...}
 * 
 * 				// "ROLE_USER", "ROLE_ADMIN" 권한을 모두 보유한 인증된 사용자만 요청할 수 있다.
 *				@Secured("ROLE_ADMIN")
 * 				@DeleteMapping("/del")
 * 				public String myInfo() {...}
 * 			}
 */


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {
	
	/*
	 * SecurityFilterChain을 생성해서 반환한다.
	 * 		- SecurityFilterChain은 인증을 처리하는 여러 개의 시큐리티 필터를 담는 필터 체인이다.
	 * 		- 아래의 메소드는 인증/인가 관련 보안 설정을 포함하는 SecurityFilterChain을 구성해서 반환하고,
	 * 		  반환된 SecurityFilterChain은 스프링 컨테이너의 빈으로 등록된다.
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				// 사이트간 요청위조 방지 기능을 비활성화 시킨다.
				.csrf()
					.disable()			
				// 요청에 의한 보안 검사 설정을 시작함
//					.authorizeRequests()
//					// http://localhost/에 대한 요청을 허가한다.
//					.antMatchers("/").permitAll()
//					// "/login", "/logout", "/emp/add"에 대한 요청을 허가한다.
//					.antMatchers("/emp/loginform", "/emp/login", "/emp/logout", "/emp/add").permitAll()
//					// 위에서 정의한 요청외의 모든 요청에 대해서 보안검사를 수행한다.
//					.anyRequest().authenticated()
//				.and()
					// Form 로그인 인증 기능을 사용한다.
					.formLogin()
					// 사용자정의 로그인 페이지를 요청하는 URL을 지정한다.
					.loginPage("/emp/loginform")
					// 아이디에 해당하는 파라미터값 이름을 지정한다.
					.usernameParameter("email")
					// 비밀번호에 해당하는 파라미터값 이름을 지정한다.
					.passwordParameter("password")
					// Form 로그인 인증작업을 요청하는 URL을 지정한다. form태그의 action
					.loginProcessingUrl("/emp/login")
					// 로그인 성공시 재요청 URL을 지정한다.
					.defaultSuccessUrl("/")
					// 로그인 실패시 재요청 URL을 지정한다.
					.failureUrl("/emp/loginform?error=fail")
				.and()
					// 로그아웃 기능을 사용
					.logout()
					// 로그아웃을 요청하는 URL을 지정한다.
					.logoutUrl("/emp/logout")
					// 로그아웃 성공시 재요청 URL을 지정한다.
					.logoutSuccessUrl("/")
					// 세션객체를 무효화 시킨다.
					.invalidateHttpSession(true)
				.and()
					.exceptionHandling()
					.authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/emp/loginform?error=denied"))
				.and()
					.exceptionHandling()
					.accessDeniedHandler((request, response, authException) -> response.sendRedirect("/emp/loginform?error=forbidden"))
				.and()
					.build();
			
	}

	/*
	 * 비밀번호 인코딩을 지원하는 BCryptPasswordEncoder객체를 스프링 컨테이너의 빈으로 등록시킨다.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
