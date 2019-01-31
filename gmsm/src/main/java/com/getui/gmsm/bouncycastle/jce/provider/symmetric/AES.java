package com.getui.gmsm.bouncycastle.jce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;

import javax.crypto.spec.IvParameterSpec;

import com.getui.gmsm.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.getui.gmsm.bouncycastle.crypto.BufferedBlockCipher;
import com.getui.gmsm.bouncycastle.crypto.CipherKeyGenerator;
import com.getui.gmsm.bouncycastle.crypto.engines.AESFastEngine;
import com.getui.gmsm.bouncycastle.crypto.engines.AESWrapEngine;
import com.getui.gmsm.bouncycastle.crypto.engines.RFC3211WrapEngine;
import com.getui.gmsm.bouncycastle.crypto.macs.CMac;
import com.getui.gmsm.bouncycastle.crypto.modes.CBCBlockCipher;
import com.getui.gmsm.bouncycastle.crypto.modes.CFBBlockCipher;
import com.getui.gmsm.bouncycastle.crypto.modes.OFBBlockCipher;
import com.getui.gmsm.bouncycastle.jce.provider.BouncyCastleProvider;
import com.getui.gmsm.bouncycastle.jce.provider.JCEBlockCipher;
import com.getui.gmsm.bouncycastle.jce.provider.JCEKeyGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JCEMac;
import com.getui.gmsm.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import com.getui.gmsm.bouncycastle.jce.provider.JDKAlgorithmParameters;
import com.getui.gmsm.bouncycastle.jce.provider.WrapCipherSpi;

public final class AES
{
    private AES()
    {
    }
    
    public static class ECB
        extends JCEBlockCipher
    {
        public ECB()
        {
            super(new AESFastEngine());
        }
    }

    public static class CBC
       extends JCEBlockCipher
    {
        public CBC()
        {
            super(new CBCBlockCipher(new AESFastEngine()), 128);
        }
    }

    static public class CFB
        extends JCEBlockCipher
    {
        public CFB()
        {
            super(new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 128)), 128);
        }
    }

    static public class OFB
        extends JCEBlockCipher
    {
        public OFB()
        {
            super(new BufferedBlockCipher(new OFBBlockCipher(new AESFastEngine(), 128)), 128);
        }
    }

    public static class AESCMAC
        extends JCEMac
    {
        public AESCMAC()
        {
            super(new CMac(new AESFastEngine()));
        }
    }

    static public class Wrap
        extends WrapCipherSpi
    {
        public Wrap()
        {
            super(new AESWrapEngine());
        }
    }

    public static class RFC3211Wrap
        extends WrapCipherSpi
    {
        public RFC3211Wrap()
        {
            super(new RFC3211WrapEngine(new AESFastEngine()), 16);
        }
    }

    public static class KeyGen
        extends JCEKeyGenerator
    {
        public KeyGen()
        {
            this(192);
        }

        public KeyGen(int keySize)
        {
            super("AES", keySize, new CipherKeyGenerator());
        }
    }

    public static class KeyGen128
        extends KeyGen
    {
        public KeyGen128()
        {
            super(128);
        }
    }

    public static class KeyGen192
        extends KeyGen
    {
        public KeyGen192()
        {
            super(192);
        }
    }

    public static class KeyGen256
        extends KeyGen
    {
        public KeyGen256()
        {
            super(256);
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
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for AES parameter generation.");
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
                params = AlgorithmParameters.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
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
            return "AES IV";
        }
    }

    public static class Mappings
        extends HashMap
    {
        /**
         * These three got introduced in some messages as a result of a typo in an
         * early document. We don't produce anything using these OID values, but we'll
         * read them.
         */
        private static final String wrongAES128 = "2.16.840.1.101.3.4.2";
        private static final String wrongAES192 = "2.16.840.1.101.3.4.22";
        private static final String wrongAES256 = "2.16.840.1.101.3.4.42";

        public Mappings()
        {
            put("AlgorithmParameters.AES", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$AlgParams");
            put("Alg.Alias.AlgorithmParameters." + wrongAES128, "AES");
            put("Alg.Alias.AlgorithmParameters." + wrongAES192, "AES");
            put("Alg.Alias.AlgorithmParameters." + wrongAES256, "AES");
            put("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes128_CBC, "AES");
            put("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes192_CBC, "AES");
            put("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes256_CBC, "AES");

            put("AlgorithmParameterGenerator.AES", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$AlgParamGen");
            put("Alg.Alias.AlgorithmParameterGenerator." + wrongAES128, "AES");
            put("Alg.Alias.AlgorithmParameterGenerator." + wrongAES192, "AES");
            put("Alg.Alias.AlgorithmParameterGenerator." + wrongAES256, "AES");
            put("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes128_CBC, "AES");
            put("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes192_CBC, "AES");
            put("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes256_CBC, "AES");

            put("Cipher.AES", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$ECB");
            put("Alg.Alias.Cipher." + wrongAES128, "AES");
            put("Alg.Alias.Cipher." + wrongAES192, "AES");
            put("Alg.Alias.Cipher." + wrongAES256, "AES");
            put("Cipher." + NISTObjectIdentifiers.id_aes128_ECB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$ECB");
            put("Cipher." + NISTObjectIdentifiers.id_aes192_ECB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$ECB");
            put("Cipher." + NISTObjectIdentifiers.id_aes256_ECB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$ECB");
            put("Cipher." + NISTObjectIdentifiers.id_aes128_CBC, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$CBC");
            put("Cipher." + NISTObjectIdentifiers.id_aes192_CBC, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$CBC");
            put("Cipher." + NISTObjectIdentifiers.id_aes256_CBC, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$CBC");
            put("Cipher." + NISTObjectIdentifiers.id_aes128_OFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$OFB");
            put("Cipher." + NISTObjectIdentifiers.id_aes192_OFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$OFB");
            put("Cipher." + NISTObjectIdentifiers.id_aes256_OFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$OFB");
            put("Cipher." + NISTObjectIdentifiers.id_aes128_CFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$CFB");
            put("Cipher." + NISTObjectIdentifiers.id_aes192_CFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$CFB");
            put("Cipher." + NISTObjectIdentifiers.id_aes256_CFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$CFB");
            put("Cipher.AESWRAP", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$Wrap");
            put("Alg.Alias.Cipher." + NISTObjectIdentifiers.id_aes128_wrap, "AESWRAP");
            put("Alg.Alias.Cipher." + NISTObjectIdentifiers.id_aes192_wrap, "AESWRAP");
            put("Alg.Alias.Cipher." + NISTObjectIdentifiers.id_aes256_wrap, "AESWRAP");
            put("Cipher.AESRFC3211WRAP", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$RFC3211Wrap");

            put("KeyGenerator.AES", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen");
            put("KeyGenerator.2.16.840.1.101.3.4.2", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen128");
            put("KeyGenerator.2.16.840.1.101.3.4.22", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen192");
            put("KeyGenerator.2.16.840.1.101.3.4.42", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen256");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_ECB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen128");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_CBC, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen128");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_OFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen128");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_CFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen128");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_ECB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen192");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_CBC, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen192");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_OFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen192");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_CFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen192");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_ECB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen256");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_CBC, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen256");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_OFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen256");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_CFB, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen256");
            put("KeyGenerator.AESWRAP", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes128_wrap, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen128");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes192_wrap, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen192");
            put("KeyGenerator." + NISTObjectIdentifiers.id_aes256_wrap, "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$KeyGen256");

            put("Mac.AESCMAC", "com.getui.gmsm.bouncycastle.jce.provider.symmetric.AES$AESCMAC");
        }
    }
}
