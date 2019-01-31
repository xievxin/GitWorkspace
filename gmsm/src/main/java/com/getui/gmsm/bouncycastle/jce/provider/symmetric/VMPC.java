package com.getui.gmsm.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

import com.getui.gmsm.bouncycastle.crypto.CipherKeyGenerator;
import com.getui.gmsm.bouncycastle.crypto.engines.VMPCEngine;
import com.getui.gmsm.bouncycastle.crypto.macs.VMPCMac;
import com.getui.gmsm.bouncycastle.jce.provider.JCEKeyGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JCEMac;
import com.getui.gmsm.bouncycastle.jce.provider.JCEStreamCipher;

public final class VMPC
{
    private VMPC()
    {
    }
    
    public static class Base
        extends JCEStreamCipher
    {
        public Base()
        {
            super(new VMPCEngine(), 16);
        }
    }

    public static class KeyGen
        extends JCEKeyGenerator
    {
        public KeyGen()
        {
            super("VMPC", 128, new CipherKeyGenerator());
        }
    }

    public static class Mac
        extends JCEMac
    {
        public Mac()
        {
            super(new VMPCMac());
        }
    }

    public static class Mappings
        extends HashMap
    {
        public Mappings()
        {
            put("Cipher.VMPC", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.VMPC$Base");
            put("KeyGenerator.VMPC", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.VMPC$KeyGen");
            put("Mac.VMPCMAC", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.VMPC$Mac");
            put("Alg.Alias.Mac.VMPC", "VMPCMAC");
            put("Alg.Alias.Mac.VMPC-MAC", "VMPCMAC");
        }
    }
}
