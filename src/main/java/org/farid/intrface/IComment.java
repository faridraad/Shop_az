package org.farid.intrface;


import org.farid.model.domain.CommentNewRequest;
import org.farid.model.dto.BaseDTO;

public interface IComment {
    BaseDTO newComment(CommentNewRequest commentNewRequest);
}
