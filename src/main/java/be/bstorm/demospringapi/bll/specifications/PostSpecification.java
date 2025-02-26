package be.bstorm.demospringapi.bll.specifications;

import be.bstorm.demospringapi.dl.entities.Comment;
import be.bstorm.demospringapi.dl.entities.Post;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<Post> joinComments() {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class) {
                Fetch<Post, Comment> commentsFetch = root.fetch("comments", JoinType.LEFT);
                commentsFetch.fetch("user", JoinType.LEFT);
            }
            return criteriaBuilder.conjunction();
        };
    }


    public static Specification<Post> joinOwner() {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class) {
                root.fetch("owner", JoinType.LEFT);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Post> joinLikers() {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType() != Long.class) {
                root.fetch("likers", JoinType.LEFT);
            }
            return criteriaBuilder.conjunction();
        };
    }
}
