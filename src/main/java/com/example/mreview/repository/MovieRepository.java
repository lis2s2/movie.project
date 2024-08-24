package com.example.mreview.repository;

import com.example.mreview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // 영화 목록 페이지에 영화, 영화 이미지, 리뷰 수, 평점 출력
    // coalesce(r.grade, 0): 평점이 null인 경우 0으로 대체
//    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m"
//            + " left outer join MovieImage mi on mi.movie = m"
//            + " left outer join Review r on r.movie = m group by m") // 먼저 입력된 이미지 번호와 연결
    @Query("select m, i, count(r) from Movie m" +
            " left join MovieImage i on i.movie = m" +
            " and i.inum = (select max(i2.inum) from MovieImage i2 where i2.movie = m)" + // 나중에 추가된 이미지 번호와 연결
            " left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable); // 페이징 처리

    // 영화의 평점/리뷰 개수 출력
//    @Query("select m, mi from Movie m" +
//            " left outer join MovieImage mi on mi.movie = m" +
//            " where m.mno = :mno")
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct(r)) from Movie m" +
            " left outer join MovieImage mi on mi.movie = m" +
            " left outer join Review r on r.movie = m" + // 리뷰 조인
            " where m.mno = :mno group by mi")
    List<Object[]> getMovieWithAll(Long mno); // 특정 영화 조회
}
