package de.ellpeck.actuallyadditions.mod.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.StringJoiner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

public class RefHelp {

    public static class UnableToFindMethodException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UnableToFindMethodException(String[] methodNames, Exception failed) {
            super(failed);
        }

        public UnableToFindMethodException(Throwable failed) {
            super(failed);
        }
    }

    public static class UnableToFindClassException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UnableToFindClassException(String[] classNames, @Nullable Exception err) {
            super(err);
        }
    }

    public static class UnableToAccessFieldException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UnableToAccessFieldException(String[] fieldNames, Exception e) {
            super(e);
        }

        public UnableToAccessFieldException(Exception e) {
            super(e);
        }
    }

    public static class UnableToFindFieldException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UnableToFindFieldException(String[] fieldNameList, Exception e) {
            super(e);
        }

        public UnableToFindFieldException(Exception e) {
            super(e);
        }
    }

    public static class UnknownConstructorException extends RuntimeException {
        public UnknownConstructorException(final String message) {
            super(message);
        }
    }

    public static Field findField(Class<?> clazz, String... fieldNames) {
        Exception failed = null;
        for (String fieldName : fieldNames) {
            try {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } catch (Exception e) {
                failed = e;
            }
        }
        throw new UnableToFindFieldException(fieldNames, failed);
    }

    @Nonnull
    public static Field findField(@Nonnull Class<?> clazz, @Nonnull String fieldName, @Nullable String fieldObfName) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkArgument(StringUtils.isNotEmpty(fieldName), "Field name cannot be empty");

        String nameToFind = FMLLaunchHandler.isDeobfuscatedEnvironment() ? fieldName : MoreObjects.firstNonNull(fieldObfName, fieldName);

        try {
            Field f = clazz.getDeclaredField(nameToFind);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            throw new UnableToFindFieldException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, @Nullable E instance, int fieldIndex) {
        try {
            Field f = classToAccess.getDeclaredFields()[fieldIndex];
            f.setAccessible(true);
            return (T) f.get(instance);
        } catch (Exception e) {
            throw new UnableToAccessFieldException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, E instance, String... fieldNames) {
        try {
            return (T) findField(classToAccess, fieldNames).get(instance);
        } catch (Exception e) {
            throw new UnableToAccessFieldException(fieldNames, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, @Nullable E instance, String fieldName, @Nullable String fieldObfName) {
        try {
            return (T) findField(classToAccess, fieldName, fieldObfName).get(instance);
        } catch (Exception e) {
            throw new UnableToAccessFieldException(e);
        }
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, T instance, E value, int fieldIndex) {
        try {
            Field f = classToAccess.getDeclaredFields()[fieldIndex];
            f.setAccessible(true);
            f.set(instance, value);
        } catch (Exception e) {
            throw new UnableToAccessFieldException(e);
        }
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, T instance, E value, String... fieldNames) {
        try {
            findField(classToAccess, fieldNames).set(instance, value);
        } catch (Exception e) {
            throw new UnableToAccessFieldException(fieldNames, e);
        }
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, @Nullable T instance, @Nullable E value, String fieldName, @Nullable String fieldObfName) {
        try {
            findField(classToAccess, fieldName, fieldObfName).set(instance, value);
        } catch (Exception e) {
            throw new UnableToAccessFieldException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Class<? super Object> getClass(ClassLoader loader, String... classNames) {
        Exception err = null;
        for (String className : classNames) {
            try {
                return (Class<? super Object>) Class.forName(className, false, loader);
            } catch (Exception e) {
                err = e;
            }
        }

        throw new UnableToFindClassException(classNames, err);
    }

    /**
     * Finds a method with the specified name and parameters in the given class and makes it accessible.
     * Note: for performance, store the returned value and avoid calling this repeatedly.
     * <p>
     * Throws an exception if the method is not found.
     *
     * @param clazz          The class to find the method on.
     * @param methodName     The name of the method to find (used in developer environments, i.e. "getWorldTime").
     * @param methodObfName  The obfuscated name of the method to find (used in obfuscated environments, i.e. "getWorldTime").
     *                       If the name you are looking for is on a class that is never obfuscated, this should be null.
     * @param parameterTypes The parameter types of the method to find.
     * @return The method with the specified name and parameters in the given class.
     */
    @Nonnull
    public static Method findMethod(@Nonnull Class<?> clazz, @Nonnull String methodName, @Nullable String methodObfName, Class<?>... parameterTypes) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkArgument(StringUtils.isNotEmpty(methodName), "Method name cannot be empty");

        String nameToFind = FMLLaunchHandler.isDeobfuscatedEnvironment() ? methodName : MoreObjects.firstNonNull(methodObfName, methodName);

        try {
            Method m = clazz.getDeclaredMethod(nameToFind, parameterTypes);
            m.setAccessible(true);
            return m;
        } catch (Exception e) {
            throw new UnableToFindMethodException(e);
        }
    }

    /**
     * Finds a constructor in the specified class that has matching parameter types.
     *
     * @param klass The class to find the constructor in
     * @param parameterTypes The parameter types of the constructor.
     * @param <T> The type
     * @return The constructor
     * @throws NullPointerException if {@code klass} is null
     * @throws NullPointerException if {@code parameterTypes} is null
     * @throws UnknownConstructorException if the constructor could not be found
     */
    @Nonnull
    public static <T> Constructor<T> findConstructor(@Nonnull final Class<T> klass, @Nonnull final Class<?>... parameterTypes) {
        Preconditions.checkNotNull(klass, "class");
        Preconditions.checkNotNull(parameterTypes, "parameter types");

        try {
            Constructor<T> constructor = klass.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        } catch (final NoSuchMethodException e) {
            final StringBuilder desc = new StringBuilder();
            desc.append(klass.getSimpleName());

            StringJoiner joiner = new StringJoiner(", ", "(", ")");
            for (Class<?> type : parameterTypes) {
                joiner.add(type.getSimpleName());
            }
            desc.append(joiner);

            throw new UnknownConstructorException("Could not find constructor '" + desc.toString() + "' in " + klass);
        }
    }
}