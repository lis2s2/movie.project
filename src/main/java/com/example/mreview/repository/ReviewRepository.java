package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 영화에 대한 리뷰 출력
    // @EntityGraph: 엔티티의 특정한 속성을 같이 로딩하도록 표시 -> 문제 해결
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie); // Lazy로 인해 한번에 Review와 Member 객체 조회X

    // 특정 회원 삭제
    // @Modifying: update나 delete를 사용하기 위해서 반드시 필요
    @Modifying
    @Query("delete from Review mr where mr.member = :member")
    void deleteByMember(Member member);
}
