/*******************************************************************************
 * Copyright (c) 2011, 2014 University of Southampton.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    University of Southampton - initial API and implementation
 *******************************************************************************/

package ac.soton.eventb.roseEditor.propertySections.editTables;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.context.Context;
import org.eventb.emf.core.context.ContextPackage;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractDerivedPredicateTablePropertySection;

/**
 * The Axioms tab table section.
 *
 * @author Colin Snook
 */

public class ContextAxiomsPropertySection extends AbstractDerivedPredicateTablePropertySection implements IFilter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof Context) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected String getButtonLabelText() {
		return "Axiom";//$NON-NLS-1$
	}

	@Override
	protected EReference getFeature() {
		return ContextPackage.eINSTANCE.getContext_Axioms();
	}

}