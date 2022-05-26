package org.farid.intrface;


import org.farid.model.domain.VoteNewRequest;
import org.farid.model.dto.BaseDTO;

public interface IVote {
    BaseDTO newVote(VoteNewRequest voteNewRequest);
}
