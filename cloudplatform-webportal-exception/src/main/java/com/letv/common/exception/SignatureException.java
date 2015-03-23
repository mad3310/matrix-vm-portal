/*
 * Pprun's Public Domain.
 */
package com.letv.common.exception;

/**
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SignatureException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4281599271170392797L;

	/**
     * Creates a new instance of <code>SecurityServiceException</code> without detail message.
     */
    public SignatureException() {
    }

    /**
     * Constructs an instance of <code>SecurityServiceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SignatureException(String msg) {
        super(msg);
    }

    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignatureException(Throwable cause) {
        super(cause);
    }
}
