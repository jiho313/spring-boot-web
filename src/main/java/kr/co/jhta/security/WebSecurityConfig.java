package kr.co.jhta.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * @@EnableWebSecurity
 * 		- 이 어노테이션은 Spring Security 기능을 활성화하고, Spring Security 관련 설정을
 * 		  자동으로 구성한다.
 */
@Configuration
@EnableWebSecurity
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
					.authorizeRequests()
					// http://localhost/에 대한 요청을 허가한다.
					.antMatchers("/").permitAll()
					// "/login", "/logout", "/emp/add"에 대한 요청을 허가한다.
					.antMatchers("/emp/loginform", "/emp/login", "/emp/logout", "/emp/add").permitAll()
					// 위에서 정의한 요청외의 모든 요청에 대해서 보안검사를 수행한다.
					.anyRequest().authenticated()
				.and()
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
