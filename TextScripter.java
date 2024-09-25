import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

/**
 * A class that plays an associated text file as an adventure.
 */
class TextScripter {
    private HashSet<String> memory;
    private Vector<String> lines;
    private Vector<String[]> lineCommands;
    private Vector<Integer> options;
    private HashMap<String, Integer> flags;
    private int optionIndex = 0;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("script.txt");
        TextScripter ts = new TextScripter(file);

        ts.run();
    }

    public TextScripter(File file) throws FileNotFoundException {
        memory = new HashSet<String>();
        lines = new Vector<>();
        lineCommands = new Vector<>();
        options = new Vector<>();
        flags = new HashMap<String, Integer>();
        optionIndex = 0;

        loadText(file);
    }

    public void run() {
        int i = 0;
        while(lines.get(i).startsWith("#")) {
            i++;
        }
        System.out.println("Starting...");
        printFromLine(i);

        Scanner keyboard = new Scanner(System.in);
        while(options.size() > 0) {
            System.out.print(": ");
            int input = keyboard.nextInt()-1;
            System.out.println("");
            
            if(input == -1){
                System.out.println("Exiting...");
                break;
            }

            try {
                runOption(input);
            } catch (Exception e) {
                System.out.println("Pick an avaliable option.");
            }
        }
        keyboard.close();
    }

    public void addMemory(String mem) {
        memory.add(mem);
    }

    public void removeMemory(String mem) {
        memory.remove(mem);
    }

    public boolean containsMemory(String mem) {
        return memory.contains(mem);
    }

    private void loadText(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        int index = -1;

        while(reader.hasNextLine()) {
            index++;
            lineCommands.add(index, null);
            String text = reader.nextLine();
            if(text.startsWith("#")) {
                lines.add("#");
                continue;
            }
            
            if(text.endsWith("]")) {
                int splitIndex = text.indexOf('[');
                String[] command = text.substring(splitIndex+1, text.length()-1).split(",");
                text = text.substring(0, splitIndex);
                lineCommands.add(index, command);
            }

            if(text.startsWith("<")) {
                int splitIndex = text.indexOf('>');
                String flag = text.substring(1, splitIndex).trim();
                text = text.substring(splitIndex+1);
                flags.put(flag, index);
            }

            lines.add(text.trim());
        }
        reader.close();
    }

    private void runOption(int index) {
        index = options.get(index);
        String[] command = lineCommands.get(index);
        Vector<Integer> prime = new Vector<>();
        options.clear();
        optionIndex = 0;

        for(String s : command) {
            s = s.trim();
            if(s.endsWith("@")) {
                String cmd = s.substring(0, s.length()-1);
                addMemory(cmd);
            }
            else if(s.endsWith("!")) {
                String cmd = s.substring(0, s.length()-1);
                removeMemory(cmd);   
            }
            else if(s.contains("-")) {
                int i = index+1;
                for(Character c : s.toCharArray()) {
                    if(lines.get(i).equals(""))
                        i++;
                    prime.add(i++);
                }
            }
            else if(flags.containsKey(s)) {
                prime.add(flags.get(s));
            }
            else {
                prime.add(Integer.decode(s));
            }
        }

        for(Integer i : prime)
            printFromLine(i);
    }

    private void printFromLine(int index) {
        String line = lines.get(index);

        if(line.startsWith("{")) {
            int splitIndex = line.indexOf('}');
            String[] command = line.substring(1, splitIndex).split(",");
            line = line.substring(splitIndex+1).trim();
            boolean o = false;
            for(String s : command) {
                s = s.trim();
                if(s.endsWith("?")){
                    if(!containsMemory(s.substring(0,s.length()-1)))
                        return;
                }
                else if(s.startsWith("!")) {
                    String cmd = s.substring(1, s.length());
                    if(containsMemory(cmd))
                        return;
                }
                
                //always last
                else if(s.equals("o"))
                    o = true;
            }
            if(o) {
                options.add(index);
                line = (++optionIndex) + ". " + line;
            }
        }

        Vector<Integer> prime = new Vector<Integer>();
        if(line.endsWith("}")) {
            int splitIndex = line.indexOf("{");
            String[] command = line.substring(splitIndex+1, line.length()-1).split(",");
            line = line.substring(0, splitIndex).trim();
            for(String s : command) {
                s = s.trim();
                if(s.contains("-")) {
                    int i = index+1;
                    for(Character c : s.toCharArray()) {
                        if(lines.get(i).equals(""))
                            i++;
                        prime.add(i++);
                    }
                }
                else if(flags.containsKey(s)) {
                    prime.add(flags.get(s));
                }
                else {
                    prime.add(Integer.decode(s));
                }
            }

        }

        System.out.println(line);

        for(Integer i : prime) {
            printFromLine(i);
        }
    }

}