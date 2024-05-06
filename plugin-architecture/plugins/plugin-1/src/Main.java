package src;
public class Main {
    public boolean PreExecute() {
        System.out.println("PreExecute: plugin 1");
        return true;
    }
    public boolean Execute() {
        System.out.println("Execute: plugin 1");
        return true;
    }
    public boolean PostExecute() {
        System.out.println("PostExecute: plugin 1");
        return true;
    }
}