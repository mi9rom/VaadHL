/* Copyright 2015 Mirosław Romaniuk (mi9rom@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadHL.utl.state;

import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Window;

/**
 * Screen object info (size and position) also getting  and restoring  recorded
 * parameters of a screen object. 
 *
 */
public class ScreenInfo extends VHLState {
	private static final long serialVersionUID = 8599015837532339093L;
	private float height;
	private Unit heightUnits;
	private float width;
	private Unit widthUnits;
	private int positionX;
	private int positionY;

	public ScreenInfo() {
		super(1);
	}

	/**
	 * Gets size info from a screen object
	 * 
	 * @param si
	 *            si the screen object implementing the {@link Sizeable}
	 *            interface
	 */
	public void sizeFrom(Sizeable si) {
		height = si.getHeight();
		heightUnits = si.getHeightUnits();
		width = si.getWidth();
		widthUnits = si.getWidthUnits();
	}

	/**
	 * Gets information from a window.
	 * 
	 * @param wi
	 *            the Window
	 */
	public void readFrom(Window wi) {
		positionX = wi.getPositionX();
		positionY = wi.getPositionY();
		sizeFrom(wi);
	}

	/**
	 * Changes size of a screen object
	 * 
	 * @param si
	 *            the screen object implementing the {@link Sizeable} interface
	 */
	public void applyTo(Sizeable si) {
		si.setHeight(getHeight(), getHeightUnits());
		si.setWidth(getWidth(), getWidthUnits());
	}

	/**
	 * Changes size and position of a window.
	 * 
	 * @param wi
	 *            the window
	 */
	public void applyToWin(Window wi) {
		applyTo((Sizeable) wi);
		wi.setPosition(getPositionX(), getPositionY());
	}
	
	// getters setters
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Unit getHeightUnits() {
		return heightUnits;
	}

	public void setHeightUnits(Unit heightUnits) {
		this.heightUnits = heightUnits;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public Unit getWidthUnits() {
		return widthUnits;
	}

	public void setWidthUnits(Unit widthUnits) {
		this.widthUnits = widthUnits;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

}
