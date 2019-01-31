package com.getui.gmsm.bouncycastle.crypto.tls;

import com.getui.gmsm.bouncycastle.crypto.DSA;
import com.getui.gmsm.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.getui.gmsm.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.getui.gmsm.bouncycastle.crypto.signers.ECDSASigner;

class TlsECDSASigner extends TlsDSASigner
{
    public boolean isValidPublicKey(AsymmetricKeyParameter publicKey)
    {
        return publicKey instanceof ECPublicKeyParameters;
    }

    protected DSA createDSAImpl()
    {
        return new ECDSASigner();
    }
}
