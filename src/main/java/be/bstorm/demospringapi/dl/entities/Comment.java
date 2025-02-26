package be.bstorm.demospringapi.dl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true,of = {"content"}) @ToString(of = {"content"})
public class Comment extends BaseEntity<Long> {

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user;

    public Comment(String content) {
        this.content = content;
    }
}
