package com.getui.gmsm.bouncycastle.jce.provider;

import com.getui.gmsm.bouncycastle.util.CollectionStore;
import com.getui.gmsm.bouncycastle.util.Selector;
import com.getui.gmsm.bouncycastle.x509.X509CollectionStoreParameters;
import com.getui.gmsm.bouncycastle.x509.X509StoreParameters;
import com.getui.gmsm.bouncycastle.x509.X509StoreSpi;

import java.util.Collection;

public class X509StoreAttrCertCollection
    extends X509StoreSpi
{
    private CollectionStore _store;

    public X509StoreAttrCertCollection()
    {
    }

    public void engineInit(X509StoreParameters params)
    {
        if (!(params instanceof X509CollectionStoreParameters))
        {
            throw new IllegalArgumentException(params.toString());
        }

        _store = new CollectionStore(((X509CollectionStoreParameters)params).getCollection());
    }

    public Collection engineGetMatches(Selector selector)
    {
        return _store.getMatches(selector);
    }
}
