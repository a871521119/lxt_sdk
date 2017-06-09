package com.lxt.sdk.lib.xutils.http.loader;





import com.lxt.sdk.lib.xutils.cache.DiskCacheEntity;
import com.lxt.sdk.lib.xutils.http.request.UriRequest;

import java.io.InputStream;


/*package*/ class IntegerLoader extends Loader<Integer> {
    @Override
    public Loader<Integer> newInstance() {
        return new IntegerLoader();
    }

    @Override
    public Integer load(InputStream in) throws Throwable {
        return 100;
    }

    @Override
    public Integer load(UriRequest request) throws Throwable {
        request.sendRequest();
        return request.getResponseCode();
    }

    @Override
    public Integer loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable {
        return null;
    }

    @Override
    public void save2Cache(UriRequest request) {

    }
}
