package com.xianghong.life.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author jackie chen
 * @create 4/16/20
 * @description JsonUtil
 */
public class JsonUtil {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new LongDateTypeAdapter())
            .registerTypeAdapter(String.class, new StringDeserializer())
            .registerTypeAdapter(Boolean.class, new BooleanDeserializer())
            .registerTypeAdapter(boolean.class, new BooleanDeserializer())
            .create();
    private static Gson gsonThrift = new GsonBuilder().registerTypeAdapter(Date.class, new LongDateTypeAdapter())
            .setExclusionStrategies(new JSONLogStrategy()).create();
    private static JsonParser parser = new JsonParser();

    public static Gson getGson() {
        return gson;
    }

    /**
     * 专为解析Thrift实体类Gson实例
     */
    public static Gson getGsonThrift() {
        return gsonThrift;
    }

    /**
     * 忽略isSet：序列化json中没有isSet属性
     *
     * @param object
     * @return
     * @see JsonUtil#toBeanIgnoreIsSet(java.lang.String, java.lang.Class)
     */
    public static String toJsonIgnoreIsSet(Object object) {
        return Objects.isNull(object) ? null : gsonThrift.toJsonTree(object).toString();
    }

    /**
     * 忽略isSet：反序列化后的isSet方法 不能用！不能用！不能用！
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @see JsonUtil#toJsonIgnoreIsSet(java.lang.Object)
     */
    public static <T> T toBeanIgnoreIsSet(String json, Class<T> clazz) {
        return gsonThrift.fromJson(json, clazz);
    }

    /**
     * 解析string to {@link JsonObject}
     *
     * @param jsonStr json串
     * @return {@link JsonObject}
     */
    public static JsonObject parseObject(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        return parser.parse(jsonStr).getAsJsonObject();
    }

    /**
     * 解析 string to Object
     *
     * @param jsonStr json串
     * @param clazz   target Object class
     * @param <T>     泛型
     * @return Object
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }

    /**
     * 解析 string to Object
     *
     * @param jsonStr json串
     * @param type    target Object Type. <p>当前方法提供了type工具方法 {@link JsonUtil#getType(Type, Type...)} {@link JsonUtil#getMultiArgsType(Type, Type...)} </p>
     * @param <T>     泛型
     * @return Object
     */
    public static <T> T parseObject(String jsonStr, Type type) {
        return gson.fromJson(jsonStr, type);
    }

    /**
     * 解析 string to List&lt;Object&gt;
     *
     * @param jsonStr json串
     * @param clazz   target Object class
     * @param <T>     泛型
     * @return List&lt;Object&gt;
     */
    public static <T> List<T> parseObjectList(String jsonStr, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(List.class, clazz);
        return gson.fromJson(jsonStr, type);
    }

    /**
     * 解析 string to Set&lt;Object&gt;
     *
     * @param jsonStr json串
     * @param clazz   target Object class
     * @param <T>     泛型
     * @return Set&lt;Object&gt;
     */
    public static <T> Set<T> parseObjectSet(String jsonStr, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(Set.class, clazz);
        return gson.fromJson(jsonStr, type);
    }

    /**
     * 解析 string to Map&lt;Key,Value&gt;
     *
     * @param jsonStr    json串
     * @param keyClazz   target Key class
     * @param valueClazz target Value class
     * @param <K>        key泛型
     * @param <V>        value泛型
     * @return Map&lt;Key, Value&gt;
     */
    public static <K, V> Map<K, V> parseObjectMap(String jsonStr, Class<K> keyClazz, Class<V> valueClazz) {
        Type type = ParameterizedTypeImpl.getMultiArgsParameterized(Map.class, keyClazz, valueClazz);
        return gson.fromJson(jsonStr, type);
    }

    /**
     * 解析 string to  {@link JsonArray}
     *
     * @param jsonStr json串
     * @return {@link JsonArray}
     */
    public static JsonArray parseArray(String jsonStr) {
        if (null == jsonStr) {
            return null;
        }
        return parser.parse(jsonStr).getAsJsonArray();
    }

    /**
     * Object to json串
     *
     * @param object Object
     * @return json串
     */
    public static String toJsonString(Object object) {
        return gson.toJsonTree(object).toString();
    }

    /**
     * 构建单参数的泛型Type
     * <p>
     * 例: 构建 Response&lt;List&lt;Student&gt;&gt;
     * JsonUtil.getType(Response.class, List.class, Student.class)
     * </p>
     * <p>
     * 如果是想构建嵌套类型
     * 例: List&lt;Map&lt;Integer, Student&gt;&gt;
     * JsonUtil.getType(List.class, JsonUtil.getMultiArgsType(Map.class, Integer.class, Student.class))
     * </p>
     *
     * @param raw   当前类真实类型
     * @param types 嵌套泛型类
     * @return 单参数的泛型Type
     */
    public static Type getType(Type raw, Type... types) {
        return ParameterizedTypeImpl.get(raw, types);
    }

    /**
     * 构建多参数泛型Type
     * <p>
     * 例: 构建Map&lt;Integer, String&gt;
     * JsonUtil.getMultiArgsType(Map.class, Integer.class, String.class);
     * </p>
     * <p>
     * 如果是想构建嵌套类型
     * 例: Response&lt;String, List&lt;Student&gt;&gt;
     * JsonUtil.getMultiArgsType(Response.class, String.class, JsonUtil.getType(List.class, Student.class))
     * </p>
     * <p>
     * 构建只嵌套一层的单参数泛型Type也可使用该方法, 但不推荐使用, 此时建议使用{@link JsonUtil#getType(Type, Type...)}
     * 例: List&lt;Student&gt;可以使用JsonUtil.getMultiArgsType(List.class, Student.class)构造
     * </p>
     *
     * @param raw  当前类真实类型
     * @param args 泛型参数
     * @return 多参数泛型type
     */
    public static Type getMultiArgsType(Type raw, Type... args) {
        return ParameterizedTypeImpl.getMultiArgsParameterized(raw, args);
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        private final Type raw;
        private final Type owner;
        private final Type[] args;

        private ParameterizedTypeImpl(Type raw, Type singleArg) {
            this(null, raw, singleArg);
        }

        private ParameterizedTypeImpl(Type owner, Type raw, Type... args) {
            this.owner = owner;
            this.raw = raw;
            this.args = args;
        }

        public static ParameterizedTypeImpl get(@NonNull Type rawType, @NonNull Type... types) {
            final int length = types.length;
            if (length > 1) {
                Type parameterizedType = new ParameterizedTypeImpl(types[length - 2], types[length - 1]);
                Type[] newTypes = Arrays.copyOf(types, length - 1);
                newTypes[newTypes.length - 1] = parameterizedType;
                return get(rawType, newTypes);
            }
            return new ParameterizedTypeImpl(rawType, types[0]);
        }

        private static ParameterizedTypeImpl getMultiArgsParameterized(@NonNull Type raw, @NonNull Type... args) {
            return new ParameterizedTypeImpl(null, raw, args);
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return owner;
        }
    }

    private static class LongDateTypeAdapter extends TypeAdapter<Date> {

        @Override
        public void write(JsonWriter jsonWriter, Date date) throws IOException {
            if (Objects.isNull(date)) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(date.getTime());
        }

        @Override
        public Date read(JsonReader jsonReader) throws IOException {
            if (Objects.isNull(jsonReader)) {
                return null;
            }
            return new Date(Long.parseLong(jsonReader.nextString()));
        }
    }

    private static class JSONLogStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getName().equals("bitSet");
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }

    private static class StringDeserializer implements JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json instanceof JsonPrimitive) {
                return json.getAsString();
            }
            return json.toString();
        }
    }

    private static class BooleanDeserializer implements JsonDeserializer<Boolean> {
        @Override
        public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json instanceof JsonPrimitive) {
                if (((JsonPrimitive) json).isBoolean()) {
                    return json.getAsBoolean();
                } else if (((JsonPrimitive) json).isNumber()) {
                    int i = json.getAsInt();
                    if (i == 0) {
                        return false;
                    } else if (i == 1) {
                        return true;
                    } else {
                        throw new JsonParseException("not number boolean");
                    }
                } else if (((JsonPrimitive) json).isString()) {
                    String s = json.getAsString();
                    if (s.equals("0")) {
                        return false;
                    } else if (s.equals("1")) {
                        return true;
                    } else {
                        throw new JsonParseException("not string boolean");
                    }
                }
            }
            throw new JsonParseException("not primitive");
        }
    }
}
