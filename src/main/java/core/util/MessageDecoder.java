package core.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder<T> implements Decoder.Text<T> {

    private static final Gson gson = new Gson();
    @Override
    public T decode(String s) throws DecodeException {
        T t = gson.fromJson(s,new TypeToken<T>(){}.getType());
        return t;
    }

    @Override
    public boolean willDecode(String s) {
        return s!=null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
