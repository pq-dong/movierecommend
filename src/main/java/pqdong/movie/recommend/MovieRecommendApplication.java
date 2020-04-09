package pqdong.movie.recommend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication()
public class MovieRecommendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieRecommendApplication.class, args);
    }
}
