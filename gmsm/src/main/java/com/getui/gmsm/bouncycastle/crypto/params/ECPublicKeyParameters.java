package com.getui.gmsm.bouncycastle.crypto.params;

import com.getui.gmsm.bouncycastle.ec.ECPoint;

public class ECPublicKeyParameters
    extends ECKeyParameters
{
    ECPoint Q;

    public ECPublicKeyParameters(
        ECPoint             Q,
        ECDomainParameters  params)
    {
        super(false, params);
        this.Q = Q;
    }

    public ECPoint getQ()
    {
        return Q;
    }
}
