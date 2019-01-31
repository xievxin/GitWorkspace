package com.getui.gmsm.bouncycastle.crypto.tls;

import java.security.SecureRandom;

import com.getui.gmsm.bouncycastle.crypto.CryptoException;
import com.getui.gmsm.bouncycastle.crypto.Signer;
import com.getui.gmsm.bouncycastle.crypto.params.AsymmetricKeyParameter;

interface TlsSigner
{
    byte[] calculateRawSignature(SecureRandom random, AsymmetricKeyParameter privateKey, byte[] md5andsha1)
        throws CryptoException;

    Signer createVerifyer(AsymmetricKeyParameter publicKey);

    boolean isValidPublicKey(AsymmetricKeyParameter publicKey);
}
