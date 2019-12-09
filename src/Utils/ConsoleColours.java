package Utils;

import java.util.ArrayList;
import java.util.List;

public class ConsoleColours {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset
    // Regular Colours
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    public List<String> commands = new ArrayList<>();

    public ConsoleColours() {
        this.commands.add(RESET);
        this.commands.add(BLACK);
        this.commands.add(RED);
        this.commands.add(GREEN);
        this.commands.add(YELLOW);
        this.commands.add(BLUE);
        this.commands.add(PURPLE);
        this.commands.add(CYAN);
        this.commands.add(WHITE);
    }
}
