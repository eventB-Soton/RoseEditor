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

package ac.soton.eventb.roseEditor.propertySections.abstracts;

import org.eclipse.emf.ecore.EAttribute;
import org.eventb.emf.core.CorePackage;

/**
 *  provides name, theorem and predicate features for derived predicate tables,
 *  and uses a larger column for the predicate
 *
 * * @author cfs
 */

public abstract class AbstractDerivedPredicateTablePropertySection extends AbstractTablePropertySection{

	@Override
	protected EAttribute getFeatureForCol(final int col) {
		switch (col) {
		case 0 : return CorePackage.eINSTANCE.getEventBNamed_Name();
		case 1 : return CorePackage.eINSTANCE.getEventBDerived_Theorem();
		case 2 : return CorePackage.eINSTANCE.getEventBPredicate_Predicate();
		case 3 : return CorePackage.eINSTANCE.getEventBCommented_Comment();
		default : return null;
		}
	}

	@Override
	protected int columnWidth(final int col){
		switch (col) {
		case 0 : return 150;	//name field
		case 1 : return 60;		//derived
		case 2 : return 400;	//predicate field
		case 3 : return 400;	//comment field
		default : return -1;	//unknown
		}
	}


	@Override
	protected boolean isRodinKeyboard(final int col) {
		return col==2 ? true : false;
	}
	
	@Override
	protected boolean isMulti(final int col){
		switch (col) {
		case 0 : return false;	//name
		case 1 : return false;	//derived field
		case 2 : return true;	//predicate field
		case 3 : return true;	//comment field
		default : return false;	//unknown
		}
	}

	
}
