package src;
public class Main {
    public boolean PreExecute() {
        System.out.println("PreExecute: plugin 2");
        return true;
    }
    public boolean Execute() {
        System.out.println("Execute: plugin 2");
        return true;
    }
    public boolean PostExecute() {
        System.out.println("PostExecute: plugin 2");
        return true;
    }
}