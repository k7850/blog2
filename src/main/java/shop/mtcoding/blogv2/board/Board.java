package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = true)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // db조회 1번만 하고, pk만 가져오고 user의 세부필드는 null로 / EAGER이면 불일치 해결해야해서 db에 user로 또찾음
    private User user;

    // OneToMany는 LAZY전략이 디폴트
    @JsonIgnoreProperties({"board"}) // 데이터줄땐 메세지컨버터로 lazy로딩돼서 무한참조일어나서 막을려고
    // @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 상위 엔터티에서 하위 엔터티로 모든 작업을 전파 (보드삭제시 보드에 달린 리플도 삭제됨)
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY) // 포링키가 아니라는 설정(Reply의 이 클래스 변수명)
    private List<Reply> replies = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;


    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    
}