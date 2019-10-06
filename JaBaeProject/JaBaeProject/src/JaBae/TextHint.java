package JaBae;

import java.awt.Color;

import java.awt.event.FocusEvent;

import java.awt.event.FocusListener;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import javax.swing.JTextField;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

public class TextHint implements FocusListener, DocumentListener, PropertyChangeListener {

	private final JTextField textfield;

	private boolean isEmpty;

	private Color hintColor;

	private Color foregroundColor;

	private final String hintText;

	public TextHint(final JTextField textfield, String hintText) {

		super();

		this.textfield = textfield;

		this.hintText = hintText;

		this.hintColor = Color.LIGHT_GRAY;

		textfield.addFocusListener(this);

		registerListeners();

		updateState();

		if (!this.textfield.hasFocus()) {

			focusLost(null);

		}

	}

	public void delete() {

		unregisterListeners();

		textfield.removeFocusListener(this);

	}

	private void registerListeners() {

		textfield.getDocument().addDocumentListener(this);

		textfield.addPropertyChangeListener("foreground", this);

	}

	private void unregisterListeners() {

		textfield.getDocument().removeDocumentListener(this);

		textfield.removePropertyChangeListener("foreground", this);

	}

	public Color getGhostColor() {

		return hintColor;

	}

	public void setGhostColor(Color hintColor) {

		this.hintColor = hintColor;

	}

	private void updateState() {

		isEmpty = textfield.getText().length() == 0;

		foregroundColor = textfield.getForeground();

	}

	@Override

	public void focusGained(FocusEvent e) {

		if (isEmpty) {

			unregisterListeners();

			try {

				textfield.setText("");

				textfield.setForeground(foregroundColor);

			} finally {

				registerListeners();

			}

		}

	}

	@Override

	public void focusLost(FocusEvent e) {

		if (isEmpty) {

			unregisterListeners();

			try {

				textfield.setText(hintText);

				textfield.setForeground(hintColor);

			} finally {

				registerListeners();

			}

		}

	}

	@Override

	public void propertyChange(PropertyChangeEvent evt) {

		updateState();

	}

	@Override

	public void changedUpdate(DocumentEvent e) {

		updateState();

	}

	@Override

	public void insertUpdate(DocumentEvent e) {

		updateState();

	}

	@Override

	public void removeUpdate(DocumentEvent e) {

		updateState();

	}

}
