package com.javajober.template.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.javajober.core.error.exception.Exception404;
import com.javajober.core.message.ErrorMessage;
import com.javajober.entity.AddSpace;
import com.javajober.entity.SpaceType;

public interface AddSpaceRepository extends Repository<AddSpace, Long> {
	Optional<AddSpace> findBySpaceTypeAndId(SpaceType spaceType, Long memberId);

	default AddSpace getBySpaceTypeAndId(final SpaceType spaceType, final Long memberId){
		return findBySpaceTypeAndId(spaceType,memberId)
			.orElseThrow(() -> new Exception404(ErrorMessage.ADD_SPACE_NOT_FOUND));
	}
}
