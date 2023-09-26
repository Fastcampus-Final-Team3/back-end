package com.javajober.template.repository;

import org.springframework.data.repository.Repository;

import com.javajober.entity.TemplateBlock;

public interface TemplateBlockRepository extends Repository<TemplateBlock, Long> {

	TemplateBlock save(TemplateBlock templateBlock);


}
