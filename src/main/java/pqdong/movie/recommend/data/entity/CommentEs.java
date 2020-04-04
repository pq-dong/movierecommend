package pqdong.movie.recommend.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


/**
 * CommentEs
 * 评论
 * @author pqdong
 * @since 2020/03/31
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "comment", type = "comment")
public class CommentEs {

    @Id
    private Long commentId;

    // 用户id
    @Field(type= FieldType.Text)
    private String userMd;

    // 用户名称
    @Field(type= FieldType.Text)
    private String userName;

    // 用户头像
    @Field(type= FieldType.Text)
    private String userAvatar;

    // 电影id
    @Field(type= FieldType.Long)
    private Long movieId;

    // 电影名称
    @Field(type= FieldType.Text)
    private String movieName;

    // 电影评论
    @Field(type= FieldType.Text)
    private String content;

    // 赞同数
    @Field(type= FieldType.Long)
    private Long votes;

    // 评论时间
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Field(format= DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss",type= FieldType.Date)
    private Date commentTime;

}