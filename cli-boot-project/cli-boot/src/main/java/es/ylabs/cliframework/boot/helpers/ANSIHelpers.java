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

package es.ylabs.cliframework.boot.helpers;

public class ANSIHelpers {
    public final static String ANSI_RESET = "\u001B[0m";

    public final static String ANSI_BOLD = "\u001B[1m";
    public final static String ANSI_DIM = "\u001B[2m";
    public final static String ANSI_ITALIC = "\u001B[3m";
    public final static String ANSI_UNDERLINE = "\u001B[4m";
    public final static String ANSI_BLINK = "\u001B[5m";
    public final static String ANSI_RAPID_BLINK = "\u001B[6m";
    public final static String ANSI_REVERSE = "\u001B[7m";
    public final static String ANSI_INVISIBLE = "\u001B[8m";
    public final static String ANSI_STRIKETHROUGH = "\u001B[9m";

    public final static String ANSI_BLACK = "\u001B[30m";
    public final static String ANSI_RED = "\u001B[31m";
    public final static String ANSI_GREEN = "\u001B[32m";
    public final static String ANSI_YELLOW = "\u001B[33m";
    public final static String ANSI_BLUE = "\u001B[34m";
    public final static String ANSI_PURPLE = "\u001B[35m";
    public final static String ANSI_CYAN = "\u001B[36m";
    public final static String ANSI_WHITE = "\u001B[37m";

    public final static String ANSI_CLEAR = "\033[H\033[2J";


    public static void printGreenAndBold(String message) {
        System.out.println(ANSI_GREEN + ANSI_BOLD + message + ANSI_RESET);
    }

    public static void printRedAndBold(String message) {
        System.out.println(ANSI_RED + ANSI_BOLD + message + ANSI_RESET);
    }

    public static void printYellowAndBold(String message) {
        System.out.println(ANSI_YELLOW + ANSI_BOLD + message + ANSI_RESET);
    }
}
