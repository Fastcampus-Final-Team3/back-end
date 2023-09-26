package com.javajober.template.dto;



import java.util.ArrayList;
import java.util.List;

import com.javajober.entity.TemplateAuth;
import com.javajober.entity.TemplateBlock;

import lombok.Getter;

@Getter
public class TemplateBlockRequest {
	private String templateUUID;
	private String templateTitle;
	private String templateDescription;
	private List<Long> hasAccessTemplateAuth;
	private List<Long> hasDenyTemplateAuth;

	public TemplateBlockRequest(){
	}

	public static TemplateBlock toEntity(TemplateBlockRequest templateBlockRequest,TemplateAuth templateAuth){
		return TemplateBlock.builder()
			.templateUUID(templateBlockRequest.getTemplateUUID())
			.templateTitle(templateBlockRequest.getTemplateTitle())
			.templateDescription(templateBlockRequest.getTemplateDescription())
			.templateAuth(templateAuth)
			.build();
	}
	public List<Long> getAllAuthIds() {
		List<Long> allAuthIds = new ArrayList<>();
		allAuthIds.addAll(hasAccessTemplateAuth);
		allAuthIds.addAll(hasDenyTemplateAuth);
		return allAuthIds;
	}


}

