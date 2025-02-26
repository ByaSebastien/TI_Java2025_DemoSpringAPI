package be.bstorm.demospringapi.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode(callSuper = true,of = {"title"}) @ToString(of = {"title","content"})
public class Post extends BaseEntity<Long>{

    @Column(nullable = false,length = 100)
    @Setter
    private String title;

    @Column(nullable = false)
    @Setter
    private String content;

    @Column
    @Setter
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Setter
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    private final Set<User> likers;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    @Setter
    private List<Comment> comments;

    public Post() {
        this.likers = new HashSet<>();
        this.comments = new ArrayList<>();
    }

    public Post(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public Post(String title, String content, String image, User owner) {
        this(title,content);
        this.image = image;
        this.owner = owner;
    }

    public void addLiker(User liker) {
        this.likers.add(liker);
    }

    public void removeLiker(User liker) {
        this.likers.remove(liker);
    }
}
