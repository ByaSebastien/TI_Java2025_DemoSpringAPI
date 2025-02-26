package be.bstorm.demospringapi.dal.repositories;

import be.bstorm.demospringapi.dl.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    //Pagination impossible avec les join fetch + risque de duplication du au comment (abort the mission)
    @Query("""
    SELECT DISTINCT p 
    FROM Post p 
    LEFT JOIN FETCH p.owner 
    LEFT JOIN FETCH p.comments c
    LEFT JOIN FETCH c.user
    WHERE c.id IN (
        SELECT c2.id FROM Comment c2 
        WHERE c2.post = p 
        ORDER BY c2.createdAt DESC
        LIMIT 3
    )
""")
    List<Post> test();
}
