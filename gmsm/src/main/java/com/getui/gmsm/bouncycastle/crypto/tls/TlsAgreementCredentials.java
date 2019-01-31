package com.getui.gmsm.bouncycastle.crypto.tls;

import java.io.IOException;

import com.getui.gmsm.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface TlsAgreementCredentials extends TlsCredentials
{
    byte[] generateAgreement(AsymmetricKeyParameter serverPublicKey) throws IOException;
}
