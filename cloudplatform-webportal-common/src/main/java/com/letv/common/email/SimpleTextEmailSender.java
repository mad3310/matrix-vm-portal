/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.email;

/**
 * Construct a text email and sent to the target email address.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public interface SimpleTextEmailSender {
    void send(String subject, String username, String content, String email);
}
