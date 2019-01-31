package com.getui.gmsm.bouncycastle.jce.interfaces;

import java.security.PublicKey;

import com.getui.gmsm.bouncycastle.ec.ECPoint;

/**
 * interface for elliptic curve public keys.
 */
public interface ECPublicKey
    extends ECKey, PublicKey
{
    /**
     * return the public point Q
     */
    public ECPoint getQ();
}
