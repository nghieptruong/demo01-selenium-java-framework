package utils.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final Gson gson = new Gson();

    public static List<Map<String, String>> readJson(Class<?> testClass) {
        try {
            String className = testClass.getSimpleName();

            String packageName = testClass.getPackage().getName();
            String relativePath = packageName.substring(packageName.indexOf("testcases") + "testcases".length() + 1)
                    .replace(".", "/");

            String filePath = "data/" + relativePath + "/" + className + ".json";

            InputStream is = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(filePath);

            if (is == null) {
                throw new RuntimeException("Cannot find test data file: " + filePath);
            }

            Type type = new TypeToken<List<Map<String, String>>>() {}.getType();

            return gson.fromJson(new InputStreamReader(is), type);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    public static List<Map<String, String>> readJson(Method method) {
        return readJson(method.getDeclaringClass());
    }
}
