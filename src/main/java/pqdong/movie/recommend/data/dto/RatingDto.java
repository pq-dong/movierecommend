package pqdong.movie.recommend.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {

    private Long movieId;

    private Long userId;

    private Float rating;
}
