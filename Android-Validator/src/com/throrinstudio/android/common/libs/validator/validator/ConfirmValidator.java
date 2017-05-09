package com.throrinstudio.android.common.libs.validator.validator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.EditText;

import com.throrinstudio.android.common.libs.validator.AbstractValidator;
import com.throrinstudio.android.common.libs.validator.R;

/**
 * Validates whether the given value is between a range of values
 * 
 * @author Dixon D'Cunha (Exikle)
 */
public class ConfirmValidator extends AbstractValidator {

	/**
	 * 验证的EditText
	 */
	EditText myEditText;


	/**
	 * The error Id from the string resource
	 */
	private int mErrorMessage; // Your custom error message

	/**
	 * Default error message if none specified
	 */
	private static final int DEFAULT_ERROR_MESSAGE_RESOURCE = R.string.validator_length;

	public ConfirmValidator(Context c, EditText editText) {
		super(c, DEFAULT_ERROR_MESSAGE_RESOURCE);
		myEditText = editText;
	}

  public ConfirmValidator(Context c, int errorMessageRes, Drawable errorDrawable, EditText editText) {
    super(c, errorMessageRes, errorDrawable);
	  myEditText = editText;
  }

  /**
	 * @param editText
	 * @param errorMessageRes
	 */
	public ConfirmValidator(Context c, EditText editText,
							int errorMessageRes) {
		super(c, errorMessageRes);
		mErrorMessage = errorMessageRes;
		myEditText = editText;
	}

	@Override
	public String getMessage() {
		return getContext().getString(mErrorMessage);
	}

	/**
	 * Checks is value is between given range
	 * @return true if between range; false if outside of range
	 */
	@Override
	public boolean isValid(String value) {
		if (value != null && value.length() > 0) {
			if (myEditText != null && value.length() > 0) {
				if (value.equals(myEditText.getText().toString())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else
			return false;
	}
}