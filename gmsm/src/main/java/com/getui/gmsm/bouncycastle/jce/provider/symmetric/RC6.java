package com.getui.gmsm.bouncycastle.jce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;

import javax.crypto.spec.IvParameterSpec;

import com.getui.gmsm.bouncycastle.crypto.BufferedBlockCipher;
import com.getui.gmsm.bouncycastle.crypto.CipherKeyGenerator;
import com.getui.gmsm.bouncycastle.crypto.engines.RC6Engine;
import com.getui.gmsm.bouncycastle.crypto.modes.CBCBlockCipher;
import com.getui.gmsm.bouncycastle.crypto.modes.CFBBlockCipher;
import com.getui.gmsm.bouncycastle.crypto.modes.OFBBlockCipher;
import com.getui.gmsm.bouncycastle.jce.provider.BouncyCastleProvider;
import com.getui.gmsm.bouncycastle.jce.provider.JCEBlockCipher;
import com.getui.gmsm.bouncycastle.jce.provider.JCEKeyGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JDKAlgorithmParameters;

public final class RC6
{
    private RC6()
    {
    }
    
    public static class ECB
        extends JCEBlockCipher
    {
        public ECB()
        {
            super(new RC6Engine());
        }
    }

    public static class CBC
       extends JCEBlockCipher
    {
        public CBC()
        {
            super(new CBCBlockCipher(new RC6Engine()), 128);
        }
    }

    static public class CFB
        extends JCEBlockCipher
    {
        public CFB()
        {
            super(new BufferedBlockCipher(new CFBBlockCipher(new RC6Engine(), 128)), 128);
        }
    }

    static public class OFB
        extends JCEBlockCipher
    {
        public OFB()
        {
            super(new BufferedBlockCipher(new OFBBlockCipher(new RC6Engine(), 128)), 128);
        }
    }

    public static class KeyGen
        extends JCEKeyGenerator
    {
        public KeyGen()
        {
            super("RC6", 256, new CipherKeyGenerator());
        }
    }

    public static class AlgParamGen
        extends JDKAlgorithmParameterGenerator
    {
        protected void engineInit(
            AlgorithmParameterSpec genParamSpec,
            SecureRandom random)
            throws InvalidAlgorithmParameterException
        {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for RC6 parameter generation.");
        }

        protected AlgorithmParameters engineGenerateParameters()
        {
            byte[]  iv = new byte[16];

            if (random == null)
            {
                random = new SecureRandom();
            }

            random.nextBytes(iv);

            AlgorithmParameters params;

            try
            {
                params = AlgorithmParameters.getInstance("RC6", BouncyCastleProvider.PROVIDER_NAME);
                params.init(new IvParameterSpec(iv));
            }
            catch (Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }

            return params;
        }
    }

    public static class AlgParams
        extends JDKAlgorithmParameters.IVAlgorithmParameters
    {
        protected String engineToString()
        {
            return "RC6 IV";
        }
    }

    public static class Mappings
        extends HashMap
    {
        public Mappings()
        {
            put("Cipher.RC6", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.RC6$ECB");
            put("KeyGenerator.RC6", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.RC6$KeyGen");
            put("AlgorithmParameters.RC6", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.RC6$AlgParams");
        }
    }
}
