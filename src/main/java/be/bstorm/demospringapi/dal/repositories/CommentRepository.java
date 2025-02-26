package be.bstorm.demospringapi.dal.repositories;

import be.bstorm.demospringapi.dl.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
    SELECT c 
    FROM Comment c 
    LEFT JOIN FETCH c.user
    WHERE c.post.id IN :postIds
    AND c.id IN (
        SELECT c2.id FROM Comment c2
        WHERE c2.post = c.post
        ORDER BY c2.createdAt DESC
        LIMIT 3
    )
    ORDER BY c.createdAt DESC
""")
    List<Comment> findLastThreeCommentsForPosts(List<Long> postIds);
}
