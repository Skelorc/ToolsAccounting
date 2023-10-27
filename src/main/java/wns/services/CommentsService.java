package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.dto.ContactDTO;
import wns.entity.Comment;
import wns.entity.Tools;
import wns.repo.CommentsRepo;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CommentsService {
    private final CommentsRepo commentsRepo;

    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return (List<Comment>) commentsRepo.findAll();
    }

    public void delete(long id) {
        commentsRepo.deleteById(id);
    }

    public void save(Comment comment) {
        commentsRepo.save(comment);
    }

    public Comment save(String comment) {
        Comment comments = new Comment();
        comments.setCreated(LocalDate.now());
        comments.setText(comment);
        comments.setNameOfCommentator(SecurityContextHolder.getContext().getAuthentication().getName());
        return commentsRepo.save(comments);
    }

    public void update(Comment comment, ContactDTO dto) {
        comment.setText(dto.getComment());
        commentsRepo.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment getByTools(Tools tools) {
        return commentsRepo.findByToolsId(tools.getId()).orElse(
                Comment.builder()
                        .nameOfCommentator(SecurityContextHolder.getContext().getAuthentication().getName())
                        .created(LocalDate.now())
                        .tools(tools)
                        .build()
        );
    }
}
