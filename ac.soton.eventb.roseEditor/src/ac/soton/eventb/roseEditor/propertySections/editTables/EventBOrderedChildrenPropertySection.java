/*******************************************************************************
 * Copyright (c) 2021, 2021 University of Southampton.
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

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBObject;

import ac.soton.eventb.roseEditor.propertySections.abstracts.AbstractTablePropertySection;

/**
 * The Ordered Children tab table property section.
 *
 * This property section shows the underlying list of ordered children and can be used to re-order
 * the children. The order is preserved when saving and loading into Rodin DB.
 * 
 * Other type specific collections may be derived from this list.
 * 
 * The getNewValue method (used by Add button) reflectively finds all potential sub-classes that are derived from ordered children
 * and asks the user to select the type of element to be added.
 *
 *
 * @author Colin Snook
 */

public class EventBOrderedChildrenPropertySection extends AbstractTablePropertySection implements IFilter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(final Object selected) {
		try{
			EventBObject element = (EventBObject)selected;
			if (element instanceof EventBElement) return true;
			return false;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	protected String getButtonLabelText() {
		return "Child";//$NON-NLS-1$
	}

	@Override
	protected EReference getFeature() {
		return CorePackage.Literals.EVENT_BELEMENT__ORDERED_CHILDREN;
	}

	/**
	 * Features for columns (after Kind)
	 * 
	 * (Note that we have to return something for column 0 (kind) because it does not have a EStructuralFeature.
	 *  We cannot return null as this stops the abstract basis from looking at other columns)
	 * 
	 */
	@Override
	protected EStructuralFeature getFeatureForCol(final int col) {
		switch (col) {		
		case 0 : return CorePackage.Literals.EVENT_BNAMED__NAME; //return something non-null - will be overriden with kind
		case 1 : return CorePackage.Literals.EVENT_BNAMED__NAME;
		case 2 : return CorePackage.Literals.EVENT_BELEMENT__GENERATED;
		case 3 : return CorePackage.Literals.EVENT_BCOMMENTED__COMMENT;
		default : return null;
		}
	}
	
	/**
	 * column widths
	 */
	@Override
	protected int columnWidth(final int col){
		switch (col) {			
		case 0 : return 100;	//Kind
		case 1 : return 150;	//Name
		case 2 : return 60;		//Generated
		case 3 : return 300;	//Comment
		default : return super.columnWidth(col);
		}
	}
	
	/**
	 * add a first column to give the kind of child
	 */
	@Override
	protected List<String> getValuesForRow(final Object object) {
		List<String> ret = super.getValuesForRow(object);
		ret.set(0, ((EventBElement)object).eClass().getName());
		return ret;
	}

	/**
	 * add a first column to give the kind of child
	 */
	@Override
	protected List<String> getColumnLabelText() {
		List<String> ret = super.getColumnLabelText();
		ret.set(0, "Kind");
		return ret;
	}

	/**
	 * kind is read only
	 * 
	 */
	@Override
	protected boolean isReadOnly(final int col){
		return col==0;
	}
	
	/**
	 * skip the kind column
	 */
	@Override
	protected List<?> getPossibleValues(final int col){
		return col==0? null : super.getPossibleValues(col);
	}
	
	/**
	 * This is called by the generic add button to create a new element to be added
	 * to the feature.
	 *
	 * Since the data type of the ordered children is abstract this asks the user
	 * to select the kind of element to create from all possible derived collections of the owner.
	 * 
	 */
	@Override
	protected Object getNewValue(){	
		//find potential EClass that may be derived from ordered children
		EList<EClass>possibleEClass = new BasicEList<EClass>();
		for (EReference ref : owner.eClass().getEAllReferences()) {
			EClassifier eClass = ref.getEType();
			if (	ref.isDerived() && ref.isContainment() &&
					eClass instanceof EClass &&
					CorePackage.Literals.EVENT_BELEMENT.isSuperTypeOf((EClass) eClass)
				) {
				possibleEClass.add((EClass) eClass);
			}
		}
		
		//ask user which kind of element to create
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell,new EClassLabelProvider());
		dialog.setFilter(null);
		dialog.setElements(possibleEClass.toArray());
		dialog.setTitle("Choose type of child to add");
		dialog.create();
		dialog.open();
		if (dialog.getReturnCode() == Window.CANCEL) return null;
		Object[] res = dialog.getResult();

		//create new element and return it for the add button action
		if (res.length>0 && res[0] instanceof EClass) {
			EClass eClass = (EClass)res[0];
			return eClass.getEPackage().getEFactoryInstance().create(eClass);
		}
		return null;
		
	}
	
	private class EClassLabelProvider extends LabelProvider{
		@Override
		public String getText(final Object element){
			if (element==null) return "<null>";
			else if (element instanceof EClass) return ((EClass)element).getName();
			return "<unknown element>";
		}
		@Override
		public org.eclipse.swt.graphics.Image getImage(final Object element){
			if (element==null) return null;
			else if (element instanceof EClass) {
				//List adapters = ((EventBElement)element).eAdapters();
				return null;
			}
			else return null;
		}
	}
	
}