package pqdong.movie.recommend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MovieRecommendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieRecommendApplication.class, args);
    }
}
