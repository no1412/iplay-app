package com.throrinstudio.android.common.libs.validator.validator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.throrinstudio.android.common.libs.validator.AbstractValidator;
import com.throrinstudio.android.common.libs.validator.R;

/**
 * Validates whether the given value is between a range of values
 * 
 * @author Dixon D'Cunha (Exikle)
 */
public class LengthValidator extends AbstractValidator {

	/**
	 * The start of the range
	 */
	final int MIN_LENGTH;

	/**
	 * The end of the range
	 */
	final int MAX_LENGTH;

	/**
	 * The error Id from the string resource
	 */
	private int mErrorMessage; // Your custom error message

	/**
	 * Default error message if none specified
	 */
	private static final int DEFAULT_ERROR_MESSAGE_RESOURCE = R.string.validator_length;

	public LengthValidator(Context c, int minLength, int maxLength) {
		super(c, DEFAULT_ERROR_MESSAGE_RESOURCE);
		MIN_LENGTH = minLength;
		MAX_LENGTH = maxLength;
	}

  public LengthValidator(Context c, int errorMessageRes, Drawable errorDrawable, int minLength, int maxLength) {
    super(c, errorMessageRes, errorDrawable);
	  MIN_LENGTH = minLength;
	  MAX_LENGTH = maxLength;
  }

  /**
	 * @param minLength
	 *            of the range
	 * @param maxLength
	 *            of the range
	 * @param errorMessageRes
	 */
	public LengthValidator(Context c, int minLength, int maxLength,
						   int errorMessageRes) {
		super(c, errorMessageRes);
		mErrorMessage = errorMessageRes;
		MIN_LENGTH = minLength;
		MAX_LENGTH = maxLength;
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
			if (MIN_LENGTH >= 0 && MAX_LENGTH < 0) {
				return value.length() >= MIN_LENGTH;
			} else if (MIN_LENGTH < 0 && MAX_LENGTH >= 0) {
				return value.length() <= MAX_LENGTH;
			} else if (MIN_LENGTH >= 0 && MAX_LENGTH >= 0){
				return value.length() >= MIN_LENGTH && value.length() <= MAX_LENGTH;
			} else
				return false;
		} else
			return false;
	}
}