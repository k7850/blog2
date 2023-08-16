package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@ToString
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

    @Column(nullable = true, length = 10000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // db조회 1번만 하고, pk만 가져오고 user의 세부필드는 null로 / EAGER이면 불일치 해결해야해서 db에 user로 또찾음
    private User user;

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