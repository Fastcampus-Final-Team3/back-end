package com.javajober.space.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.javajober.core.error.exception.Exception404;
import com.javajober.core.message.ErrorMessage;
import com.javajober.space.domain.AddSpace;
import com.javajober.space.domain.SpaceType;

public interface AddSpaceRepository extends Repository<AddSpace, Long> {

	List<AddSpace> findByMemberIdAndSpaceType(final Long memberId, SpaceType spaceType);

	Optional<AddSpace> findById(final Long id);


	default AddSpace findAddSpace (final Long id) {
		return findById(id)
			.orElseThrow(() -> new Exception404(ErrorMessage.ADD_SPACE_NOT_FOUND));
	}

	Optional<AddSpace> findBySpaceTypeAndId(SpaceType spaceType, Long memberId);

	default AddSpace getBySpaceTypeAndId(final SpaceType spaceType, final Long memberId){
		return findBySpaceTypeAndId(spaceType,memberId)
			.orElseThrow(() -> new Exception404(ErrorMessage.ADD_SPACE_NOT_FOUND));
	}

}
