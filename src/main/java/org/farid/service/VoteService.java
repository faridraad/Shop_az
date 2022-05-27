package org.farid.service;

import org.farid.configuration.exception.ServiceException;
import org.farid.model.domain.VoteNewRequest;
import org.farid.model.dto.BaseDTO;
import org.farid.model.dto.MetaDTO;
import org.farid.model.entity.Member;
import org.farid.model.entity.Product;
import org.farid.model.entity.Provider;
import org.farid.model.entity.Vote;
import org.farid.repository.MemberRepository;
import org.farid.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.farid.configuration.resources.ApplicationProperties;
import org.farid.service.intrface.IVote;


@Service
public class VoteService implements IVote {

    final ApplicationProperties applicationProperties;
    final VoteRepository voteRepository;
    final MemberRepository memberRepository;

    protected VoteService(
            ApplicationProperties applicationProperties,
            VoteRepository voteRepository,
            MemberRepository memberRepository
    ) {
        this.applicationProperties = applicationProperties;
        this.voteRepository = voteRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public BaseDTO newVote(VoteNewRequest voteNewRequest) {

        Product product = new Product();
        product.setId(voteNewRequest.getProductId());

        Provider provider = new Provider();
        provider.setId(voteNewRequest.getProviderId());

        Member member = memberRepository.findById(voteNewRequest.getMemberId()).orElseThrow(
                () -> ServiceException.builder()
                        .code(applicationProperties.getCode("application.message.notFoundRecord.code"))
                        .message(applicationProperties.getProperty("application.message.notFoundRecord.text"))
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()

        );
        Vote vote = new Vote();
        vote.setProduct(product);
        vote.setProvider(provider);
        vote.setMember(member);
        vote.setRate(voteNewRequest.getRate());

        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(voteRepository.save(vote))
                .build();
    }
}
