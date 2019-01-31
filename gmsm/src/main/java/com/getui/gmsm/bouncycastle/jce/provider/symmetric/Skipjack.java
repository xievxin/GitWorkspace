package com.getui.gmsm.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

import com.getui.gmsm.bouncycastle.crypto.CipherKeyGenerator;
import com.getui.gmsm.bouncycastle.crypto.engines.SkipjackEngine;
import com.getui.gmsm.bouncycastle.crypto.macs.CBCBlockCipherMac;
import com.getui.gmsm.bouncycastle.crypto.macs.CFBBlockCipherMac;
import com.getui.gmsm.bouncycastle.jce.provider.JCEBlockCipher;
import com.getui.gmsm.bouncycastle.jce.provider.JCEKeyGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JCEMac;
import com.getui.gmsm.bouncycastle.jce.provider.JDKAlgorithmParameters;

public final class Skipjack
{
    private Skipjack()
    {
    }
    
    public static class ECB
        extends JCEBlockCipher
    {
        public ECB()
        {
            super(new SkipjackEngine());
        }
    }

    public static class KeyGen
        extends JCEKeyGenerator
    {
        public KeyGen()
        {
            super("Skipjack", 80, new CipherKeyGenerator());
        }
    }

    public static class AlgParams
        extends JDKAlgorithmParameters.IVAlgorithmParameters
    {
        protected String engineToString()
        {
            return "Skipjack IV";
        }
    }

    public static class Mac
        extends JCEMac
    {
        public Mac()
        {
            super(new CBCBlockCipherMac(new SkipjackEngine()));
        }
    }

    public static class MacCFB8
        extends JCEMac
    {
        public MacCFB8()
        {
            super(new CFBBlockCipherMac(new SkipjackEngine()));
        }
    }

    public static class Mappings
        extends HashMap
    {
        public Mappings()
        {
            put("Cipher.SKIPJACK", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.Skipjack$ECB");
            put("KeyGenerator.SKIPJACK", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.Skipjack$KeyGen");
            put("AlgorithmParameters.SKIPJACK", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.Skipjack$AlgParams");
            put("Mac.SKIPJACKMAC", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.Skipjack$Mac");
            put("Alg.Alias.Mac.SKIPJACK", "SKIPJACKMAC");
            put("Mac.SKIPJACKMAC/CFB8", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.Skipjack$MacCFB8");
            put("Alg.Alias.Mac.SKIPJACK/CFB8", "SKIPJACKMAC/CFB8");
        }
    }
}
