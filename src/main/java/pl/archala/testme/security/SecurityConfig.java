package pl.archala.testme.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.archala.testme.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .authorizeRequests()

                .antMatchers("/api/exam-attempts",
                        "/api/exams",
                        "/api/exams/exam/**",
                        "/api/exams/exam/take/**",
                        "/api/exams/exam",
                        "/api/exams/exam/max-points",
                        "/api/exams/new-exam",
                        "/api/exams/new-exam/save",
                        "/api/exams/edit",
                        "/api/auth/register",
                        "/api/auth/activate/token",
                        "/api/auth/password/reset",
                        "/api/auth/password/reset/token",
                        "/api/users/findBy/username",
                        "/api/users/password"
                ).permitAll()

                .antMatchers("/api/exams/delete/**",
                        "/api/users",
                        "/api/users/role",
                        "/api/users/delete/{id}",
                        "/api/users/roles",
                        "/api/users/password/change",
                        "/api/users/findAll/paginated"
                ).hasAuthority("ADMIN")

                .antMatchers("/api/**").authenticated()

                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/api/exams")
                .permitAll()

                .and()
                .logout()
                .deleteCookies("JSESSIONID");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("http://localhost:4200")
                        .allowedHeaders("*").allowCredentials(true);
            }
        };
    }
}