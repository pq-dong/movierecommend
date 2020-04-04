package pqdong.movie.recommend.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSearchDto {
    private Integer page;

    private Integer size;

    private Long movieId;

    private String userMd;

    private String content;
}
