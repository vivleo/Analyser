package tla.analyseur;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid parameters.");
            System.exit(0);
        }

        Controller ctrl = new Controller(args[0]);
        if (ctrl.parse(args[1])) {
            System.out.println("Syntax good.");
        } else {
            System.out.println("Syntax error.");
            ctrl.printError();
        }
    }
}