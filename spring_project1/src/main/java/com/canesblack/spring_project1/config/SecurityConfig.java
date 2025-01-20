package com.canesblack.spring_project1.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
//SpringSecurity기능을 사용할려면 이 어노테이션을 써줘야한다
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 스프링 시큐리티 기능을 사용하고자 할떄 메소드안에 작성한다.
		http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())) // csrf 해킹 기법으로 보호조치를 csrf 확인
																									// 코드방법
				.cors(cors -> cors.configurationSource(corsCorsConfigurationSource())) // cors는 특정서버로만 데이터를 넘길수있도록 설정
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 세션 설정
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/", "loginPage", "logout", "/noticeCheckPage", "/register", "menu/all")
						.permitAll().requestMatchers(HttpMethod.POST, "/login").permitAll()
						.requestMatchers("/resources/**", "WEB-INF/**").permitAll()
						.requestMatchers("/noticerAdd", "noticeModifyPage").hasAnyAuthority("ADMIN", "MANAGER")
						.requestMatchers(HttpMethod.POST, "menu/add").hasAnyAuthority("ADMIN", "MANAGER")
						.requestMatchers(HttpMethod.POST, "menu/update").hasAnyAuthority("ADMIN", "MANAGER")
						.requestMatchers(HttpMethod.DELETE, "menu/delete").hasAnyAuthority("ADMIN", "MANAGER")
						.anyRequest().authenticated())

				.formLogin(
						// 로그인 실패하면 다시 로그인페이지로 이동
						login -> login.loginPage("/loginPage").loginProcessingUrl("/login")
								.failureUrl("/loginPage?error=true").usernameParameter("username")
								.passwordParameter("password")
								.successHandler(authenticationSuceessHandler())
								.permitAll())

			.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // logout url을 통해서 로그아웃이됨
			.logoutSuccessUrl("/") // 로그아웃성공후 이 url로 리다이렉팅
			.invalidateHttpSession(true) // 세션 무효화
			.deleteCookies("JSESSIONID") // 쿠키삭제
			.permitAll()
					);

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuceessHandler() {
		return new SimpleUrlAuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				// 로그인이 성공했을때 우리가 특별기능을 넣고싶을떄(권한기능) 사용자 로그인 데이터를 가져와서 세션에 넣어 로그아웃하기전까지는 세션이 진행된다
				HttpSession session = request.getSession(); // 세션기능을 가져온다
				boolean isManager = authentication.getAuthorities().stream()
						.anyMatch(grantedAuthoirity -> grantedAuthoirity.getAuthority().equals("ADMIN")
								|| grantedAuthoirity.getAuthority().equals("MANAGER"));
				if (isManager) {
					session.setAttribute("MANAGER", true);
				}
				session.setAttribute("username", authentication.getName());
				session.setAttribute("isAuthenticatied", true);
				response.sendRedirect(request.getContextPath() + "/");

				super.onAuthenticationSuccess(request, response, authentication);
			}
		};

	}

	@Bean
	public CorsConfigurationSource corsCorsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080","https://localhost:8080"));
		// localhost:8080 서버에서는 프론트에서 백엔드단 혹은 벡엔드단에서 프론트단인데 데이터를 주고받을수 있게 만든것
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;

	}

}
