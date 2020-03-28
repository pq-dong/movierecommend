package pqdong.movie.recommend.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearchDto {
    private Integer page;

    private Integer size;

    private List<String> tags;

    private String content;
}
