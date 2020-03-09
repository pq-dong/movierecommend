
package pqdong.movie.recommend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pqdong.movie.recommend.utils.LoginInterceptor;

/**
 * WebConfig
 *
 * @author pqdong
 * @since 2020/03/09
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor());
    }

}