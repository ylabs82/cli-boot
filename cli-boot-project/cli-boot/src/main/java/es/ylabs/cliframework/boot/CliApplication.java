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

package es.ylabs.cliframework.boot;

import es.ylabs.cliframework.boot.autoconfigure.annotations.CliBootApplication;
import es.ylabs.cliframework.boot.corecli.CoreCliCommands;
import es.ylabs.cliframework.boot.helpers.ANSIHelpers;
import es.ylabs.cliframework.boot.helpers.ReadString;
import es.ylabs.cliframework.boot.loaders.CliClassLoader;
import es.ylabs.cliframework.boot.loaders.CliJARLoader;
import es.ylabs.cliframework.boot.management.CommandCollection;

import java.io.File;
import java.security.CodeSource;
import java.util.Objects;

public class CliApplication {

    /**
     * Run the CliApplication.
     *
     * @param source The class that contains the main method.
     * @param args The arguments passed to the main method.
     */
    public static void run(Class<?> source, String[] args) {
        try {
            CliBootApplication cliBootApplication;
            CommandCollection commandCollection = CommandCollection.INSTANCE.getInstance();

            if (source.isAnnotationPresent(CliBootApplication.class)) {
                cliBootApplication = source.getAnnotation(CliBootApplication.class);
            } else {
                throw new RuntimeException("Not a CliBootApplication");
            }

            loadCoreCommands(cliBootApplication, commandCollection);

            CodeSource codeSource = source.getProtectionDomain().getCodeSource();
            File file = new File(codeSource.getLocation().getPath());

            if (file.isFile()) {
                CliJARLoader.loadClassesFromJAR(file, commandCollection);
            } else if (file.isDirectory()) {
                CliClassLoader.loadClassesFromDirectory(file, commandCollection);
            } else {
                throw new RuntimeException("UNKNOWN FILE TYPE");
            }

            cliLoop(commandCollection);
        } catch (Error | Exception e) {
            ANSIHelpers.printRedAndBold("Error running CliApplication");
            e.printStackTrace();
        }
    }

    /**
     * Run the CLI loop.
     *
     * @param commandCollection The command collection.
     */
    private static void cliLoop(CommandCollection commandCollection) {
        ReadString readString = new ReadString("$ ");

        while (true) {
            String command = readString.readString();

            if (!command.isBlank()) {
                try {
                    commandCollection.executeCommand(command);
                } catch (Error | Exception e) {
                    ANSIHelpers.printRedAndBold(Objects.requireNonNullElse(e.getMessage(),
                            "UNKNOWN ERROR EXECUTING COMMAND"));
                }
            }
        }
    }

    /**
     * Load the core commands if the annotation has the coreCommands attribute
     * set to true (the default value).
     *
     * @param cliBootApplication The CliBootApplication annotation.
     * @param commandCollection The command collection.
     */
    private static void loadCoreCommands(CliBootApplication cliBootApplication,
                                         CommandCollection commandCollection)
            throws Exception {
        if (cliBootApplication.coreCommands()) {
            commandCollection.addCommand("clear", CoreCliCommands::clear);
            commandCollection.addCommand("exit", CoreCliCommands::exit);
        }
    }
}
