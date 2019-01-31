package com.getui.gmsm.bouncycastle.crypto.tls;

import com.getui.gmsm.bouncycastle.crypto.DSA;
import com.getui.gmsm.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.getui.gmsm.bouncycastle.crypto.params.DSAPublicKeyParameters;
import com.getui.gmsm.bouncycastle.crypto.signers.DSASigner;

class TlsDSSSigner extends TlsDSASigner
{
    public boolean isValidPublicKey(AsymmetricKeyParameter publicKey)
    {
        return publicKey instanceof DSAPublicKeyParameters;
    }

    protected DSA createDSAImpl()
    {
        return new DSASigner();
    }
}
