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

package ac.soton.eventb.roseEditor.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;


/**
 * Maps a selected object to the  type (class) of the element it represents
 *
 * @author Colin Snook
 */

public class ElementTypeMapper implements ITypeMapper {

	/**
	 * @inheritDoc
	 */
	public Class<?> mapType(final Object object) {
		if (object instanceof EObject) {
			return ((EObject)object).getClass();
		}
		return null;
	}

}
