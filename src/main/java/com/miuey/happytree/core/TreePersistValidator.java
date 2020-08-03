package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

class TreePersistValidator extends TreeElementValidator {

	TreePersistValidator(TreeManager manager) {
		super(manager);
	}
	
	
	@Override
	void validateMandatoryElementId(TreePipeline pipeline) {
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		if (source == null || source.getId() == null) {
			throw this.throwIllegalArgumentException(TreeRepositoryMessage.
					INVALID_INPUT);
		}
	}

	@Override
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		TreeElementCore<?> convertedSource = (TreeElementCore<?>) source;
		
		if (convertedSource.isAttached()) {
			throw this.throwTreeException(TreeRepositoryMessage.
					ATTACHED_ELEMENT);
		}
	}

	@Override
	void validateDuplicatedElement(TreePipeline pipeline) throws TreeException {
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		
		TreeManager manager = getManager();
		
		Element<?> duplicatedElement = manager.getElementById(source.getId());
		if (duplicatedElement != null) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
		validateDuplicatedChildrenId(source.getChildren());
	}
}
