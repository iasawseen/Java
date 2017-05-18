package ru.spbau.mit;

/**
 * Created by ivan on 17.05.17.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ImplementorImpl implements Implementor {
    private final String outputDir;
    private StringBuilder classInStr = new StringBuilder("");
    private Set<String> methods = new HashSet<>();
    private Map<String, Boolean> isAbstract = new HashMap<>();

    ImplementorImpl(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public String implementFromDirectory(String directoryPath, String className) throws ImplementorException {
        Class<?> clazz = getClassFromDirectory(directoryPath, className);
        return processClass(clazz);
    }

    @Override
    public String implementFromStandardLibrary(String className) throws ImplementorException {
        Class<?> clazz = getClassFromSL(className);
        return processClass(clazz);
    }

    private String processClass(Class<?> clazz) throws ImplementorException{
        addPackage(clazz);
        addClassDefinition(clazz);
        String name = writeToFile(clazz);
        methods.clear();
        classInStr = new StringBuilder("");
        isAbstract.clear();
        return name;
    }

    private void addPackage(Class<?> clazz) {
        Package pack = clazz.getPackage();
        if (!pack.getName().isEmpty()) {
            classInStr.append("package ");
            classInStr.append(pack.getName());
            classInStr.append(";\n\n");
        }
    }

    private String writeToFile(Class<?> clazz) throws ImplementorException {
        String pack = clazz.getPackage().getName();
        String fileName = clazz.getSimpleName() + "Impl";

        Path path = Paths.get(outputDir, pack.split("\\."));
        File javaFile = new File(path + "/" + fileName + ".java");

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new ImplementorException("cannot create folder for .java", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(javaFile))) {
            writer.write(classInStr.toString());
        } catch (IOException e) {
            throw new ImplementorException("cannot create java file", e);
        }

        return fileName;
    }

    private Class<?> getClassFromDirectory(String directoryPath, String className) throws ImplementorException {
        File file = new File(directoryPath);
        try {
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            ClassLoader classLoader = new URLClassLoader(urls);
            return classLoader.loadClass(className);

        } catch (MalformedURLException | ClassNotFoundException e) {
            throw new ImplementorException("cannot load class from folder", e);
        }
    }

    private Class<?> getClassFromSL(String className) throws ImplementorException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ImplementorException("cannot load class from Standard Library", e);
        }
    }

    private void addClassDefinition(Class<?> clazz) throws ImplementorException {
        addClassHeader(clazz);
        addMethods(clazz);
        addClassFooter();
    }

    private void addClassHeader(Class<?> clazz) throws ImplementorException {
        int modifiers = clazz.getModifiers();
        String implOrExtend = "";

        if (Modifier.isInterface(modifiers)) {
            implOrExtend = "implements";
        } else if (Modifier.isAbstract(modifiers)) {
            implOrExtend = "extends";
        } else {
            throw new ImplementorException("Class is not Abstract");
        }

        modifiers &= ~Modifier.INTERFACE;
        modifiers &= ~Modifier.ABSTRACT;

        String modifs = Modifier.toString(modifiers);
        if (modifs.length() > 0) {
            modifs += " ";
        }

        String toConcat = modifs + "class " +
                clazz.getSimpleName() + "Impl " + implOrExtend + " " +
                clazz.getCanonicalName() + " {\n";

        classInStr.append(toConcat);
    }

    private void addClassFooter() {
        classInStr.append("}\n");
    }

    private void addMethods(Class<?> clazz) {
        addMethodsFromAncestors(clazz);
        addMethodsFromInterfaces(clazz);

        methods = isAbstract
                .entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        for (String method : methods) {
            classInStr.append(method);
        }
    }

    private void addMethodsFromAncestors(Class<?> clazz) {
        while (clazz != null) {
            if (clazz.isInterface()) {
                break;
            }
            addMethodsFrom(clazz);
            clazz = clazz.getSuperclass();
        }

        clazz = Object.class;
        addMethodsFrom(clazz);
    }

    private void addMethodsFromInterfaces(Class<?> clazz) {
        if (clazz.isInterface()) {
            addMethodsFrom(clazz);
        }

        Class<?> zuper = clazz.getSuperclass();
        if (zuper != null) {
            addMethodsFromInterfaces(zuper);
        }

        for (Class<?> interfaze : clazz.getInterfaces()) {
            addMethodsFromInterfaces(interfaze);
        }
    }

    private void addMethodsFrom(Class<?> clazz) {
        List<Method> methods = new LinkedList<>(Arrays.asList(clazz.getDeclaredMethods()));
        for (Method method : methods) {
            String methodStr = getMethodStr(method);
            isAbstract.putIfAbsent(methodStr, Modifier.isAbstract(method.getModifiers()));
        }
    }

    private String getMethodStr(Method method) {
        StringBuilder methodStr = new StringBuilder("");

        int modifiers = method.getModifiers();
        modifiers &= ~Modifier.ABSTRACT;
        modifiers &= ~Modifier.NATIVE;

        Class<?> returnType = method.getReturnType();
        Class<?>[] parameterTypes = method.getParameterTypes();

        methodStr.append("\t");
        methodStr.append(Modifier.toString(modifiers));
        methodStr.append(" ");
        methodStr.append(returnType.getCanonicalName());
        methodStr.append(" ");
        methodStr.append(method.getName());
        methodStr.append("(");
        methodStr.append(getMethodParameters(parameterTypes));
        methodStr.append(") ");
        methodStr.append(getMethodBody(returnType));

        return methodStr.toString();
    }

    private String getMethodParameters(Class<?>[] parameterTypes) {
        return IntStream.
                range(0, parameterTypes.length).
                mapToObj(index -> getParameterName(parameterTypes[index], index))
                .collect(Collectors.joining(", "));

    }

    private String getParameterName(Class<?> parameter, int index) {
        String shortName = parameter.getCanonicalName();
        String[] parts = shortName.split("(\\.)|(\\[\\])");
        shortName = parts[parts.length - 1];

        return parameter.getCanonicalName() + " " +
                Character.toLowerCase(shortName.charAt(0)) +
                shortName.substring(1) + (index + 1);

    }

    private String getMethodBody(Class<?> returnType) {
        if (returnType.getCanonicalName().equals("void")) {
            return "{}\n\n";
        } else {
            if (returnType.getName().equals("boolean")) {
                return "{\n\t\treturn false;\n\t}\n\n";
            } else if (returnType.isPrimitive()) {
                return "{\n\t\treturn 0;\n\t}\n\n";
            } else {
                return "{\n\t\treturn null;\n\t}\n\n";
            }
        }
    }
}
