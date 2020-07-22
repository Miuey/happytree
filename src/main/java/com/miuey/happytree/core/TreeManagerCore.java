package com.miuey.happytree.core;


import java.util.Collection;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.exception.TreeException;

class TreeManagerCore implements TreeManager {

	/*
	 * The transaction associated to this manager. A manager is always related
	 * to its transaction. The cardinality is always 1:1.
	 */
	private TreeTransaction transaction = TreeFactory.serviceFactory().
			createTreeTransaction(this);
	
	
	TreeManagerCore() {}
	
	
	@Override
	public <T> Element<T> cut(Element<T> from, Element<T> to)
			throws TreeException {
		validateTransaction();
		//validateTree(from, to);
		
		Element<T> parentFrom = this.getElementById(from.getParent());
		parentFrom.removeChild(from);
		
		to.addChild(from);
		from.setParent(to.getId());
		
		synchronizeElements(parentFrom, from, to);
		return from;
	}

	@Override
	public <T> Element<T> cut(Object from, Object to) throws TreeException {
		validateTransaction();
		
		Element<T> fromElement = this.getElementById(from);
		Element<T> toElement = this.getElementById(to);
		
		return this.cut(fromElement, toElement);
	}

	@Override
	public <T> Element<T> copy(Element<T> from, Element<T> to)
			throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeElement(Element<?> element) throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeElement(Object id) throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> Element<T> getElementById(Object id) throws TreeException {
		Element<T> element = null;
		validateTransaction();
		if (id != null) {
			TreeSession session = this.getTransaction().currentSession();
			Element<T> root = session.tree();
			element = searchElement(root.getChildren(), id);
		}
		return element;
	}

	@Override
	public <T> boolean containsElement(Element<T> parent, Element<T> descendant)
			throws TreeException {
		boolean result = Boolean.FALSE;
		validateTransaction();
		if (parent != null && descendant != null) {
			Element<T> element = this.searchElement(parent.getChildren(),
					descendant.getId());
			result = element != null;
		}
		return result;
	}

	@Override
	public boolean containsElement(Object parent, Object descendant)
			throws TreeException {
		boolean result = Boolean.FALSE;
		validateTransaction();
		if (parent != null && descendant != null) {
			Element<?> parentElement = this.getElementById(parent);
			Element<?> descendantElement = this.getElementById(descendant);
			
			if (parentElement != null && descendantElement != null) {
				Element<?> element = this.searchElement(parentElement.
						getChildren(), descendantElement.getId());
				result = element != null;
			}
		}
		return result;
	}

	@Override
	public boolean containsElement(Element<?> element) throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsElement(Object id) throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> Element<T> createElement(Object id, Object parent)
			throws TreeException {
		/*
		 * Initial validation processes.
		 */
		this.validateTransaction();
		
		return TreeFactory.serviceFactory().createElement(id, parent);
	}

	@Override
	public <T> Element<T> persistElement(Element<?> newElement)
			throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Element<T> updateElement(Element<?> element)
			throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeTransaction getTransaction() {
		return this.transaction;
	}

	@Override
	public <T> Element<T> root() throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	static TreeManager getTreeManagerInstance() {
		return TreeFactory.serviceFactory().createTreeManagerCore();
	}

	@SafeVarargs
	private final <T> void synchronizeElements(Element<T>... elements) {
		String sessionId = this.getTransaction().currentSession().getSessionId();
		
		TreeElementCore<T> element = null;
		for (Element<T> iterator : elements) {
			element = (TreeElementCore<T>) iterator;
			element.attach(sessionId);
		}
	}
	
	private <T> Element<T> searchElement(Collection<Element<T>> elements,
			Object id) {
		Element<T> result = null;
		if (elements == null || elements.isEmpty()) {
			return null;
		}
		for (Element<T> element : elements) {
			if (result != null) {
				return result;
			}
			if (element.getId().equals(id)) {
				result = element;
				return result;
			}
			result = searchElement(element.getChildren(), id);
		}
		return result;
	}
	
	private void validateTransaction() throws TreeException {
		TreeSessionValidator validator = TreeFactory.validatorFactory().
				createSessionValidator(this);
		
		validator.validateNoDefinedSession();
		validator.validateNoActiveSession();
	}
	
	private void validateTree(Element<?> sourceElement,
			Element<?> targetElement) throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		TreeElementValidator validator = TreeFactory.validatorFactory().
				createElementValidator(this);
		
		pipeline.addAttribute("element", sourceElement);
		pipeline.addAttribute("id", sourceElement);
		pipeline.addAttribute("targetElement", targetElement);
		
		validator.validateMandatoryElementId(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedElement(pipeline);
	}
}
