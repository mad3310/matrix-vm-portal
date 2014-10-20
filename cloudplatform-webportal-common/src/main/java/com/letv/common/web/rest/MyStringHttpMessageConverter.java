/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.web.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

/**
 * This is a workaround for the implementation of Pay Center service.
 * <p>
 * which does not have the {@literal Content-type} set
 * in the response, but it is expected Spring MVC 3. Otherwise {#NullPointExcepton} will thrown. <br />
 * And the default charset is {@literal ISO-8859-1}, which is not the case in China.
 * To be honest, troubleshooting for charset problem is always the toughest thing.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class MyStringHttpMessageConverter extends AbstractHttpMessageConverter<String> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
    private final List<Charset> availableCharsets;
    private boolean writeAcceptCharset = true;
    private Charset charset;

    public MyStringHttpMessageConverter() {
        this(DEFAULT_CHARSET);
    }

    public MyStringHttpMessageConverter(Charset charset) {
        super(new MediaType("text", "plain", charset), MediaType.ALL);
        this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
        this.charset = charset;
    }

    /**
     * Indicates whether the {@code Accept-Charset} should be written to any outgoing request.
     * <p>Default is {@code true}.
     */
    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    protected String readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException {
        MediaType contentType = inputMessage.getHeaders().getContentType();
        // set the content type if the service implementation did not set it
        if (contentType == null) {
            contentType = MediaType.TEXT_PLAIN;
        }
        Charset theCharset = contentType.getCharSet() != null ? contentType.getCharSet() : charset;
        
        return FileCopyUtils.copyToString(new InputStreamReader(inputMessage.getBody(), theCharset));
    }

    @Override
    protected Long getContentLength(String s, MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            Charset charset = contentType.getCharSet();
            try {
                return (long) s.getBytes(charset.name()).length;
            } catch (UnsupportedEncodingException ex) {
                // should not occur
                throw new InternalError(ex.getMessage());
            }
        } else {
            return null;
        }
    }

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
        if (writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        MediaType contentType = outputMessage.getHeaders().getContentType();
        // set the content type if the service implementation did not set it
        if (contentType == null) {
            contentType = MediaType.TEXT_PLAIN;
        }
        Charset theCharset = contentType.getCharSet() != null ? contentType.getCharSet() : charset;
        FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), theCharset));
    }

    /**
     * Return the list of supported {@link Charset}.
     *
     * <p>By default, returns {@link Charset#availableCharsets()}. Can be overridden in subclasses.
     *
     * @return the list of accepted charsets
     */
    protected List<Charset> getAcceptedCharsets() {
        return this.availableCharsets;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
