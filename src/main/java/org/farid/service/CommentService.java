package org.farid.service;

import org.farid.model.domain.CommentNewRequest;
import org.farid.model.dto.BaseDTO;
import org.farid.model.dto.MetaDTO;
import org.farid.model.entity.Comment;
import org.farid.model.entity.Member;
import org.farid.model.entity.Product;
import org.farid.model.entity.Provider;
import org.farid.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.farid.configuration.resources.ApplicationProperties;
import org.farid.intrface.IComment;


@Service
public class CommentService implements IComment {

    final ApplicationProperties applicationProperties;
    final CommentRepository commentRepository;

    protected CommentService(ApplicationProperties applicationProperties,
                             CommentRepository commentRepository
    ) {
        this.applicationProperties = applicationProperties;
        this.commentRepository = commentRepository;
    }


    @Override
    public BaseDTO newComment(CommentNewRequest commentNewRequest) {
        Member member  =new Member();
        member.setId(commentNewRequest.getMemberId());

        Product product   =new Product();
        product.setId(commentNewRequest.getProductId());

        Provider provider   =new Provider();
        provider.setId(commentNewRequest.getProviderId());

        Comment comment = new Comment();
        comment.setComment(commentNewRequest.getComment());
        comment.setMember(member);
        comment.setProduct(product);
        comment.setProvider(provider);

        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(commentRepository.save(comment))
                .build();
    }

}
