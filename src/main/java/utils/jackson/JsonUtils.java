package utils.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Map<String, String>> readJson(Class<?> testClass) {
        try {
            String className = testClass.getSimpleName();

            // Lấy package sau "testcases"
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

            return objectMapper.readValue(is, new TypeReference<List<Map<String, String>>>() {});

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    public static List<Map<String, String>> readJson(Method method) {
        return readJson(method.getDeclaringClass());
    }
}
