package com.getui.gmsm.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

import com.getui.gmsm.bouncycastle.crypto.CipherKeyGenerator;
import com.getui.gmsm.bouncycastle.crypto.engines.TEAEngine;
import com.getui.gmsm.bouncycastle.jce.provider.JCEBlockCipher;
import com.getui.gmsm.bouncycastle.jce.provider.JCEKeyGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JDKAlgorithmParameters;

public final class TEA
{
    private TEA()
    {
    }
    
    public static class ECB
        extends JCEBlockCipher
    {
        public ECB()
        {
            super(new TEAEngine());
        }
    }

    public static class KeyGen
        extends JCEKeyGenerator
    {
        public KeyGen()
        {
            super("TEA", 128, new CipherKeyGenerator());
        }
    }

    public static class AlgParams
        extends JDKAlgorithmParameters.IVAlgorithmParameters
    {
        protected String engineToString()
        {
            return "TEA IV";
        }
    }

    public static class Mappings
        extends HashMap
    {
        public Mappings()
        {
            put("Cipher.TEA", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.TEA$ECB");
            put("KeyGenerator.TEA", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.TEA$KeyGen");
            put("AlgorithmParameters.TEA", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.TEA$AlgParams");
        }
    }
}
