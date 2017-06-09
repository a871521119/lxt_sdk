package com.lxt.sdk.lib.xutils.http.loader;




import com.lxt.sdk.lib.xutils.cache.DiskCacheEntity;
import com.lxt.sdk.lib.xutils.common.util.IOUtil;
import com.lxt.sdk.lib.xutils.http.request.UriRequest;

import java.io.InputStream;

/*package*/ class ByteArrayLoader extends Loader<byte[]> {

    @Override
    public Loader<byte[]> newInstance() {
        return new ByteArrayLoader();
    }

    @Override
    public byte[] load(final InputStream in) throws Throwable {
        return IOUtil.readBytes(in);
    }

    @Override
    public byte[] load(final UriRequest request) throws Throwable {
        request.sendRequest();
        return this.load(request.getInputStream());
    }

    @Override
    public byte[] loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable {
        return null;
    }

    @Override
    public void save2Cache(final UriRequest request) {
    }
}
