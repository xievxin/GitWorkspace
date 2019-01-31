package com.getui.gmsm.bouncycastle.asn1.cmp;

import java.math.BigInteger;

import com.getui.gmsm.bouncycastle.asn1.ASN1Encodable;
import com.getui.gmsm.bouncycastle.asn1.ASN1EncodableVector;
import com.getui.gmsm.bouncycastle.asn1.ASN1OctetString;
import com.getui.gmsm.bouncycastle.asn1.ASN1Sequence;
import com.getui.gmsm.bouncycastle.asn1.DERInteger;
import com.getui.gmsm.bouncycastle.asn1.DERObject;
import com.getui.gmsm.bouncycastle.asn1.DEROctetString;
import com.getui.gmsm.bouncycastle.asn1.DERSequence;

public class CertStatus
    extends ASN1Encodable
{
    private ASN1OctetString certHash;
    private DERInteger certReqId;
    private PKIStatusInfo statusInfo;

    private CertStatus(ASN1Sequence seq)
    {
        certHash = ASN1OctetString.getInstance(seq.getObjectAt(0));
        certReqId = DERInteger.getInstance(seq.getObjectAt(1));

        if (seq.size() > 2)
        {
            statusInfo = PKIStatusInfo.getInstance(seq.getObjectAt(2));
        }
    }

    public CertStatus(byte[] certHash, BigInteger certReqId)
    {
        this.certHash = new DEROctetString(certHash);
        this.certReqId = new DERInteger(certReqId);
    }

    public CertStatus(byte[] certHash, BigInteger certReqId, PKIStatusInfo statusInfo)
    {
        this.certHash = new DEROctetString(certHash);
        this.certReqId = new DERInteger(certReqId);
        this.statusInfo = statusInfo;
    }

    public static CertStatus getInstance(Object o)
    {
        if (o instanceof CertStatus)
        {
            return (CertStatus)o;
        }

        if (o instanceof ASN1Sequence)
        {
            return new CertStatus((ASN1Sequence)o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public ASN1OctetString getCertHash()
    {
        return certHash;
    }

    public DERInteger getCertReqId()
    {
        return certReqId;
    }

    public PKIStatusInfo getStatusInfo()
    {
        return statusInfo;
    }

    /**
     * <pre>
     * CertStatus ::= SEQUENCE {
     *                   certHash    OCTET STRING,
     *                   -- the hash of the certificate, using the same hash algorithm
     *                   -- as is used to create and verify the certificate signature
     *                   certReqId   INTEGER,
     *                   -- to match this confirmation with the corresponding req/rep
     *                   statusInfo  PKIStatusInfo OPTIONAL
     * }
     * </pre>
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object()
    {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(certHash);
        v.add(certReqId);

        if (statusInfo != null)
        {
            v.add(statusInfo);
        }

        return new DERSequence(v);
    }
}
