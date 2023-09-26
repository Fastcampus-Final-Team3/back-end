package com.javajober.template.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.javajober.core.error.exception.Exception404;
import com.javajober.core.message.ErrorMessage;
import com.javajober.entity.TemplateBlock;

public interface TemplateBlockRepository extends Repository<TemplateBlock, Long> {

	TemplateBlock save(TemplateBlock templateBlock);

	Optional<TemplateBlock> findById(Long id);

	default TemplateBlock getById(final Long id){
		return findById(id)
			.orElseThrow(() -> new Exception404(ErrorMessage.TEMPLATE_BLOCK_NOT_FOUND));
	}


}
