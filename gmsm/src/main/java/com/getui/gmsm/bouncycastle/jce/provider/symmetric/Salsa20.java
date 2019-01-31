package com.getui.gmsm.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

import com.getui.gmsm.bouncycastle.crypto.CipherKeyGenerator;
import com.getui.gmsm.bouncycastle.crypto.engines.Salsa20Engine;
import com.getui.gmsm.bouncycastle.jce.provider.JCEKeyGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JCEStreamCipher;

public final class Salsa20
{
    private Salsa20()
    {
    }
    
    public static class Base
        extends JCEStreamCipher
    {
        public Base()
        {
            super(new Salsa20Engine(), 8);
        }
    }

    public static class KeyGen
        extends JCEKeyGenerator
    {
        public KeyGen()
        {
            super("Salsa20", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings
        extends HashMap
    {
        public Mappings()
        {
            put("Cipher.SALSA20", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.Salsa20$Base");
            put("KeyGenerator.SALSA20", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.Salsa20$KeyGen");
        }
    }
}
