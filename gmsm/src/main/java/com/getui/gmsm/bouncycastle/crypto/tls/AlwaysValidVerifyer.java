package com.getui.gmsm.bouncycastle.crypto.tls;

import com.getui.gmsm.bouncycastle.asn1.x509.X509CertificateStructure;

/**
 * A certificate verifyer, that will always return true.
 * 
 * <pre>
 * DO NOT USE THIS FILE UNLESS YOU KNOW EXACTLY WHAT YOU ARE DOING.
 * </pre>
 * 
 * @deprecated Perform certificate verification in TlsAuthentication implementation
 */
public class AlwaysValidVerifyer implements CertificateVerifyer
{
    /**
     * Return true.
     * 
     * @see com.getui.gmsm.bouncycastle.crypto.tls.CertificateVerifyer#isValid(com.getui.gmsm.bouncycastle.asn1.x509.X509CertificateStructure[])
     */
    public boolean isValid(X509CertificateStructure[] certs)
    {
        return true;
    }
}
