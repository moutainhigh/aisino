package com.aisino.domain.einvoice.impl;

import com.aisino.domain.AbstractBaseService;
import com.aisino.domain.einvoice.SignatureService;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Martin.Ou on 2014/9/5.
 */
public final class SignatureServiceImpl extends AbstractBaseService implements SignatureService {

    private static final Logger LOGGER = getLogger(SignatureServiceImpl.class);

    @Override
    public byte[] obtainSignature(byte[] pdfBytes, final Map<String, Object> signatureParamMap) throws IOException {
        return new byte[0];
    }
}
