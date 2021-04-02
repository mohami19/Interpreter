package ir.ac.kntu; 

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        HashMap<String,Integer> map = new HashMap<>();
        String end;
        do {
            end = scan.nextLine();
            end=end.trim();
            if (end.matches("(>\\s+[a-zA-Z]+|>[a-zA-Z]+)")) {
                int integer = scan.nextInt();
                input(end,integer, map);
            }
            if (end.matches("<\\s+[a-zA-Z]+|<[a-zA-Z]+")) {
                output(end,map);
            }
            if (end.matches("([a-zA-Z]+\\s+|[a-zA-Z]+)=(.{1,})")) {
                if (definFinder(end,map)) {
                    expressionFinder(end,map);
                }
            }  
        } while (!end.equals("exit_0"));
        scan.close();
    }

    public static void expressionFinder(String s,HashMap<String,Integer> map) {
        String exp = "";
        int integer = 0;
        String[] split1 = s.split("\\w+");
        String[] split2= s.split("\\W+");
        split2[0]= split2[0].trim();
        exp += split2[0];
        for (int i = split2.length-1; i > 0; i--) {
            split1[i]=split1[i].trim();
            if (split2[i].matches("\\d+")) {
                if (split1[i].matches("[+=]|([=]\\s+[+])") ) { 
                    integer+=Integer.parseInt(split2[i]);
                }
                if (split1[i].matches("[-]|=\\s+-|=-")) {
                    integer-=Integer.parseInt(split2[i]);
                }
            }
            if (split2[i].matches("[A-Za-z]{1,}") && map.get(split2[i]) !=null) { 
                if (split1[i].matches("[+=]|([=]\\s+[+])") && map.get(split2[i]) !=null) {
                    integer += map.get(split2[i]);
                }
                if (split1[i].matches("[-]|=\\s+-|=-") && map.get(split2[i]) !=null) {
                    integer -= map.get(split2[i]);
                }
            }
        }
        map.put(exp,integer);
    }

    public static void input(String s,int integer,HashMap<String,Integer> map) {
        String exp = "";
        if (s.matches(">\\s+[a-zA-Z]+")) {
            String[] split = s.split("\\s+");
            exp += split[1].trim();
        }
        if (s.matches(">[a-zA-Z]+")) {
            exp += s.substring(1,s.length()).trim();
        }
        map.put(exp,integer);
    }

    public static void output(String s,HashMap<String,Integer> map) {
        String exp= "";
        if (s.matches("<\\s+[a-zA-Z]+")) {
            String[] split = s.split("\\s+");
            exp += split[1].trim();
            if (map.get(exp) != null && map.get(exp) != 0) {
                System.out.println(map.get(exp));
            } else {
                System.out.println(exp + " is not defined");
            }
        } else {
            if (s.matches("<[a-zA-Z]+")) {
                exp += s.subSequence(1, s.length());
                exp.trim();
                if (map.get(exp) != null && map.get(exp) != 0) {
                    System.out.println(map.get(exp));
                } else {
                    System.out.println(exp+" is not defined");
                }
            }
        }
        
    }

    public static boolean definFinder(String s,HashMap<String,Integer> map) {
        int flag = 0;
        String[] split = s.split("\\W+");
        for (int i = split.length-1; i > 0; i--) {
            if (split[i].matches("[A-Za-z]{1,}")) {
                if (map.get(split[i])==null) {
                    System.out.println(split[i]+" is not defined");
                    flag++;
                }
            }
        }
        if (flag != 0) {
            return false;
        } else {
            return true;
        }
    }
}