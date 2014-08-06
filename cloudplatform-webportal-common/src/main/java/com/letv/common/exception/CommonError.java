/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.exception;
import javax.xml.bind.annotation.XmlRootElement;

import com.letv.common.BaseResponseObject;



/**
 * POJO represents an exception response from REST service.
 *
 * <b>Please note that, in order to let the jaxb2 automatically bind work, the xml root element and its elements
 * can not be {@literal static} class</b>.
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
@XmlRootElement(name = "CommonError")
public class CommonError extends BaseResponseObject {


    private String exception;

 

    public String getException() {
        return exception;
    }

    public void setException(String Exception) {
        this.exception = Exception;
    }

   

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("path=").append(path);
        s.append(", ");
        s.append("status=").append(status);
        s.append(", ");
        s.append("httpStatusCode=").append(httpStatusCode);
        s.append(", ");
        s.append("message=").append(message);
        s.append(", ");
        s.append("exception=").append(exception);
        s.append("]");

        return s.toString();
    }
}
