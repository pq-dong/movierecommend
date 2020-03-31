package pqdong.movie.recommend.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


/**
 * CommentEs
 * 评论
 * @author pqdong
 * @since 2020/03/31
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "comment", type = "comment")
public class CommentEs {

    @Id
    private Long commentId;

    //@Field(type= FieldType.Text)
    private String userMd;

    // @Field(type= FieldType.Long)
    private Long movieId;

    //@Field(type= FieldType.Text)
    private String content;

    // @Field(type= FieldType.Long)
    private Long votes;

    // @Field(format= DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss",type= FieldType.Date)
    private String commentTime;

}