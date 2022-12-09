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
import org.eclipse.swt.widgets.Text;

/**
 * An abstract implementation of a section for a field with a String property
 * value.
 *
 */

public abstract class AbstractStringPropertySection extends AbstractTextPropertySection {

	@Override
	protected boolean isCurrent(final String newText) {
		return getFeatureAsText().equals(newText);
	}

	@Override
	protected String getFeatureAsText() {
		Object feature = null;
		if (getFeature() instanceof EAttribute){
			feature = owner.eGet(getFeature());
			if (feature instanceof String) {
				return (String)feature;
			}else if (feature==null){
				return "";
			}
		}
		return "ERROR - feature not string";
	}

	@Override
	protected Object getFeatureValue(final String newText) {
		return newText;
	}
	
	protected Text getTextWidget() {
		return (Text)widget;
	}
}