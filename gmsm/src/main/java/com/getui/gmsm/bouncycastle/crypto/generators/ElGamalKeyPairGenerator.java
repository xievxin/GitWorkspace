package com.getui.gmsm.bouncycastle.crypto.generators;

import java.math.BigInteger;

import com.getui.gmsm.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.getui.gmsm.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import com.getui.gmsm.bouncycastle.crypto.KeyGenerationParameters;
import com.getui.gmsm.bouncycastle.crypto.params.DHParameters;
import com.getui.gmsm.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import com.getui.gmsm.bouncycastle.crypto.params.ElGamalParameters;
import com.getui.gmsm.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import com.getui.gmsm.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

/**
 * a ElGamal key pair generator.
 * <p>
 * This generates keys consistent for use with ElGamal as described in
 * page 164 of "Handbook of Applied Cryptography".
 */
public class ElGamalKeyPairGenerator
    implements AsymmetricCipherKeyPairGenerator
{
    private ElGamalKeyGenerationParameters param;

    public void init(
        KeyGenerationParameters param)
    {
        this.param = (ElGamalKeyGenerationParameters)param;
    }

    public AsymmetricCipherKeyPair generateKeyPair()
    {
        DHKeyGeneratorHelper helper = DHKeyGeneratorHelper.INSTANCE;
        ElGamalParameters egp = param.getParameters();
        DHParameters dhp = new DHParameters(egp.getP(), egp.getG(), null, egp.getL());  

        BigInteger x = helper.calculatePrivate(dhp, param.getRandom()); 
        BigInteger y = helper.calculatePublic(dhp, x);

        return new AsymmetricCipherKeyPair(
            new ElGamalPublicKeyParameters(y, egp),
            new ElGamalPrivateKeyParameters(x, egp));
    }
}
