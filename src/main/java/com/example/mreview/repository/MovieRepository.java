package com.example.mreview.repository;

import com.example.mreview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // 영화 목록 페이지에 영화, 영화 이미지, 리뷰 수, 평점 출력
    // coalesce(r.grade, 0): 평점이 null인 경우 0으로 대체
//    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m"
//            + " left outer join MovieImage mi on mi.movie = m"
//            + " left outer join Review r on r.movie = m group by m") // 먼저 입력된 이미지 번호와 연결
    @Query("select m, i, count(r) from Movie m"
            + " left join MovieImage i on i.movie = m"
            + " and i.inum = (select max(i2.inum) from MovieImage i2 where i2.movie = m)"
            + " left outer join Review r on r.movie = m group by m") // 나중에 추가된 이미지 번호와 연결
    Page<Object[]> getListPage(Pageable pageable);
}
