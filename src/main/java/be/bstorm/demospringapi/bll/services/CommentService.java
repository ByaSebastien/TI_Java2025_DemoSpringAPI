package be.bstorm.demospringapi.bll.services;

import be.bstorm.demospringapi.dl.entities.Comment;
import be.bstorm.demospringapi.dl.entities.User;

public interface CommentService {

    Comment addComment(Long postId, Comment comment);
}
