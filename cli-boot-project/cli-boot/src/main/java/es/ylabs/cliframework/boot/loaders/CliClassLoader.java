/*
 * MIT License
 *
 * Copyright 2024 Yago Mouriño Mendaña <ylabs82@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package es.ylabs.cliframework.boot.loaders;

import es.ylabs.cliframework.boot.autoconfigure.annotations.CliCommand;
import es.ylabs.cliframework.boot.autoconfigure.annotations.CliCommandGroup;
import es.ylabs.cliframework.boot.helpers.ANSIHelpers;
import es.ylabs.cliframework.boot.management.CommandCollection;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CliClassLoader {

    public static void loadCliClass(String className, CommandCollection commandCollection)
            throws Error, Exception {
        Class<?> groupClass = ClassLoader.getSystemClassLoader().loadClass(className);

        if (groupClass.isAnnotationPresent(CliCommandGroup.class)) {
            Object groupInstance = groupClass.getDeclaredConstructor().newInstance();

            for (Method method : groupClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(CliCommand.class)) {
                    CliCommand cliCommand = method.getAnnotation(CliCommand.class);

                    commandCollection.addCommand(cliCommand.command(), consumer -> {
                        try {
                            method.invoke(groupInstance, (Object) consumer);
                        } catch (Error | Exception e) {
                            ANSIHelpers.printRedAndBold("Error executing command "
                                    + cliCommand.command());
                        }
                    });
                }
            }
        }
    }

    public static void loadClassesFromDirectory(File directory,
                                                CommandCollection commandCollection)
            throws Error, Exception {
        if (directory.isDirectory()) {
            List<File> classFiles = new ArrayList<>();
            exploreDirectory(directory, classFiles);

            for (File classFile : classFiles) {
                if (classFile.getName().endsWith(".class")) {
                    String className = classFile.getPath()
                            .replace(directory.getPath() + "/", "")
                            .replaceAll("/", ".")
                            .replaceAll(".class", "");
                    loadCliClass(className, commandCollection);
                }
            }
        } else {
            throw new Exception("The directory is not a directory");
        }
    }

    private static void exploreDirectory(File directory, List<File> files) {
        File[] entries = directory.listFiles();

        if (entries == null) {
            return;
        }

        for (File entry : entries) {
            if (entry.isDirectory()) {
                exploreDirectory(entry, files);
            } else {
                files.add(entry);
            }
        }
    }
}
