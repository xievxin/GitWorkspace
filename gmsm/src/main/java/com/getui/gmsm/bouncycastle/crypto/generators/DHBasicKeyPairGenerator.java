package com.getui.gmsm.bouncycastle.crypto.generators;

import com.getui.gmsm.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.getui.gmsm.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import com.getui.gmsm.bouncycastle.crypto.KeyGenerationParameters;
import com.getui.gmsm.bouncycastle.crypto.params.DHKeyGenerationParameters;
import com.getui.gmsm.bouncycastle.crypto.params.DHParameters;
import com.getui.gmsm.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.getui.gmsm.bouncycastle.crypto.params.DHPublicKeyParameters;

import java.math.BigInteger;

/**
 * a basic Diffie-Hellman key pair generator.
 *
 * This generates keys consistent for use with the basic algorithm for
 * Diffie-Hellman.
 */
public class DHBasicKeyPairGenerator
    implements AsymmetricCipherKeyPairGenerator
{
    private DHKeyGenerationParameters param;

    public void init(
        KeyGenerationParameters param)
    {
        this.param = (DHKeyGenerationParameters)param;
    }

    public AsymmetricCipherKeyPair generateKeyPair()
    {
        DHKeyGeneratorHelper helper = DHKeyGeneratorHelper.INSTANCE;
        DHParameters dhp = param.getParameters();

        BigInteger x = helper.calculatePrivate(dhp, param.getRandom()); 
        BigInteger y = helper.calculatePublic(dhp, x);

        return new AsymmetricCipherKeyPair(
            new DHPublicKeyParameters(y, dhp),
            new DHPrivateKeyParameters(x, dhp));
    }
}
