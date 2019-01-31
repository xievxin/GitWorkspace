package com.getui.gmsm.bouncycastle.jce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;

import javax.crypto.spec.IvParameterSpec;

import com.getui.gmsm.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import com.getui.gmsm.bouncycastle.crypto.CipherKeyGenerator;
import com.getui.gmsm.bouncycastle.crypto.engines.SEEDEngine;
import com.getui.gmsm.bouncycastle.crypto.engines.SEEDWrapEngine;
import com.getui.gmsm.bouncycastle.crypto.modes.CBCBlockCipher;
import com.getui.gmsm.bouncycastle.jce.provider.BouncyCastleProvider;
import com.getui.gmsm.bouncycastle.jce.provider.JCEBlockCipher;
import com.getui.gmsm.bouncycastle.jce.provider.JCEKeyGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JDKAlgorithmParameters;
import com.getui.gmsm.bouncycastle.jce.provider.WrapCipherSpi;

public final class SEED
{
    private SEED()
    {
    }
    
    public static class ECB
        extends JCEBlockCipher
    {
        public ECB()
        {
            super(new SEEDEngine());
        }
    }

    public static class CBC
       extends JCEBlockCipher
    {
        public CBC()
        {
            super(new CBCBlockCipher(new SEEDEngine()), 128);
        }
    }

    public static class Wrap
        extends WrapCipherSpi
    {
        public Wrap()
        {
            super(new SEEDWrapEngine());
        }
    }

    public static class KeyGen
        extends JCEKeyGenerator
    {
        public KeyGen()
        {
            super("SEED", 128, new CipherKeyGenerator());
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
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for SEED parameter generation.");
        }

        protected AlgorithmParameters engineGenerateParameters()
        {
            byte[] iv = new byte[16];

            if (random == null)
            {
                random = new SecureRandom();
            }

            random.nextBytes(iv);

            AlgorithmParameters params;

            try
            {
                params = AlgorithmParameters.getInstance("SEED", BouncyCastleProvider.PROVIDER_NAME);
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
            return "SEED IV";
        }
    }

    public static class Mappings
        extends HashMap
    {
        public Mappings()
        {
            put("AlgorithmParameters.SEED", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.SEED$AlgParams");
            put("Alg.Alias.AlgorithmParameters." + KISAObjectIdentifiers.id_seedCBC, "SEED");

            put("AlgorithmParameterGenerator.SEED", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.SEED$AlgParamGen");
            put("Alg.Alias.AlgorithmParameterGenerator." + KISAObjectIdentifiers.id_seedCBC, "SEED");

            put("Cipher.SEED", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.SEED$ECB");
            put("Cipher." + KISAObjectIdentifiers.id_seedCBC, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.SEED$CBC");

            put("Cipher.SEEDWRAP", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.SEED$Wrap");
            put("Alg.Alias.Cipher." + KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, "SEEDWRAP");

            put("KeyGenerator.SEED", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.SEED$KeyGen");
            put("KeyGenerator." + KISAObjectIdentifiers.id_seedCBC, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.SEED$KeyGen");
            put("KeyGenerator." + KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.SEED$KeyGen");
        }
    }
}
