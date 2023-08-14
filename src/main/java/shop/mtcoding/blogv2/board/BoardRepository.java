package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * save, findById, findAll, count 저절로 만들어줌
 */

public interface BoardRepository extends JpaRepository<Board, Integer>{

}
