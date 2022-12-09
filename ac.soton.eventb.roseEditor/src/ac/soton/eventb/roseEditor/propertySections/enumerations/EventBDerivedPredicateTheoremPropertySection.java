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

package ac.soton.eventb.roseEditor.propertySections.enumerations;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.viewers.IFilter;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBObject;
import org.eventb.emf.core.EventBPredicate;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractEnumerationPropertySection;


/**
 * A section for the derived predicate, theorem boolean value
 *
 * @author cfs
 */
public class EventBDerivedPredicateTheoremPropertySection extends AbstractEnumerationPropertySection  implements IFilter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof EventBPredicate) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected EAttribute getFeature() {
		return CorePackage.eINSTANCE.getEventBDerived_Theorem();
	}

}