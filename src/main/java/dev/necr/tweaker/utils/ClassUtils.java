package dev.necr.tweaker.utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@UtilityClass
public class ClassUtils {

    /**
     * Gets all fields from a class
     *
     * @param abstractClass the class
     *
     * @return subclasses {@link List<Field>}
     *
     * @throws IOException see {@link IOException}
     * @throws ClassNotFoundException see {@link ClassNotFoundException}
     */
    public static List<Class<?>> findClassesExtendingAbstractClass(Class<?> abstractClass) throws IOException, ClassNotFoundException {
        List<Class<?>> subclasses = new ArrayList<>();
        String packageName = abstractClass.getPackage().getName();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        Class<?> clazz = Class.forName(className);
                        if (abstractClass.isAssignableFrom(clazz) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                            subclasses.add(clazz);
                        }
                    }
                }
            }
        }

        return subclasses;
    }
}
