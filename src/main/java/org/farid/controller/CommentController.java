package org.farid.controller;


import org.farid.model.domain.CommentNewRequest;
import org.farid.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("v1/comments")
public class CommentController {

    final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?>  newComment(@Valid @RequestBody CommentNewRequest commentNewRequest){
        return new ResponseEntity<>(commentService.newComment(commentNewRequest), HttpStatus.CREATED);
    }
}
