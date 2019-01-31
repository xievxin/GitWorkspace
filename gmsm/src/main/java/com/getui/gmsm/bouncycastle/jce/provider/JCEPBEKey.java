package com.getui.gmsm.bouncycastle.jce.provider;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;

import com.getui.gmsm.bouncycastle.asn1.DERObjectIdentifier;
import com.getui.gmsm.bouncycastle.crypto.CipherParameters;
import com.getui.gmsm.bouncycastle.crypto.PBEParametersGenerator;
import com.getui.gmsm.bouncycastle.crypto.params.KeyParameter;
import com.getui.gmsm.bouncycastle.crypto.params.ParametersWithIV;

public class JCEPBEKey
    implements PBEKey
{
    String              algorithm;
    DERObjectIdentifier oid;
    int                 type;
    int                 digest;
    int                 keySize;
    int                 ivSize;
    CipherParameters    param;
    PBEKeySpec          pbeKeySpec;
    boolean             tryWrong = false;

    /**
     * @param param
     */
    public JCEPBEKey(
        String              algorithm,
        DERObjectIdentifier oid,
        int                 type,
        int                 digest,
        int                 keySize,
        int                 ivSize,
        PBEKeySpec          pbeKeySpec,
        CipherParameters    param)
    {
        this.algorithm = algorithm;
        this.oid = oid;
        this.type = type;
        this.digest = digest;
        this.keySize = keySize;
        this.ivSize = ivSize;
        this.pbeKeySpec = pbeKeySpec;
        this.param = param;
    }

    public String getAlgorithm()
    {
        return algorithm;
    }

    public String getFormat()
    {
        return "RAW";
    }

    public byte[] getEncoded()
    {
        if (param != null)
        {
            KeyParameter    kParam;
            
            if (param instanceof ParametersWithIV)
            {
                kParam = (KeyParameter)((ParametersWithIV)param).getParameters();
            }
            else
            {
                kParam = (KeyParameter)param;
            }
            
            return kParam.getKey();
        }
        else
        {
            if (type == PBE.PKCS12)
            {
                return PBEParametersGenerator.PKCS12PasswordToBytes(pbeKeySpec.getPassword());
            }
            else
            {   
                return PBEParametersGenerator.PKCS5PasswordToBytes(pbeKeySpec.getPassword());
            }
        }
    }
    
    int getType()
    {
        return type;
    }
    
    int getDigest()
    {
        return digest;
    }
    
    int getKeySize()
    {
        return keySize;
    }
    
    int getIvSize()
    {
        return ivSize;
    }
    
    CipherParameters getParam()
    {
        return param;
    }

    /* (non-Javadoc)
     * @see javax.crypto.interfaces.PBEKey#getPassword()
     */
    public char[] getPassword()
    {
        return pbeKeySpec.getPassword();
    }

    /* (non-Javadoc)
     * @see javax.crypto.interfaces.PBEKey#getSalt()
     */
    public byte[] getSalt()
    {
        return pbeKeySpec.getSalt();
    }

    /* (non-Javadoc)
     * @see javax.crypto.interfaces.PBEKey#getIterationCount()
     */
    public int getIterationCount()
    {
        return pbeKeySpec.getIterationCount();
    }
    
    public DERObjectIdentifier getOID()
    {
        return oid;
    }
    
    void setTryWrongPKCS12Zero(boolean tryWrong)
    {
        this.tryWrong = tryWrong; 
    }
    
    boolean shouldTryWrongPKCS12()
    {
        return tryWrong;
    }
}
