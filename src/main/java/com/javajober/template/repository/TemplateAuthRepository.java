package com.javajober.template.repository;

import java.util.List;
import java.util.Optional;

import com.javajober.core.error.exception.Exception404;
import com.javajober.core.message.ErrorMessage;
import com.javajober.entity.TemplateAuth;
import com.javajober.entity.TemplateBlock;

import org.springframework.data.repository.Repository;


public interface TemplateAuthRepository extends Repository<TemplateAuth, Long> {
	Optional<TemplateAuth> findByAuthMemberId(Long authMemberId);

	default TemplateAuth getByAuthMemberId(final Long authMemberId){
		return findByAuthMemberId(authMemberId)
			.orElseThrow(() -> new Exception404(ErrorMessage.TEMPLATE_AUTH_NOT_FOUND));
	}

	TemplateAuth save(TemplateAuth templateAuth);

	TemplateAuth delete(TemplateAuth auth);

	List<TemplateAuth> findByTemplateBlockId(Long templateBlockId);

	List<TemplateAuth> findByTemplateBlock(TemplateBlock block);
}
