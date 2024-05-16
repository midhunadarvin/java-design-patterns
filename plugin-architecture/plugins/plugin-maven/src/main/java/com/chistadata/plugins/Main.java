package com.chistadata.plugins;

/**
 * Hello world!
 *
 */
public class Main
{
    public boolean PreExecute() {
        System.out.println("PreExecute: plugin maven");
        return true;
    }

    public boolean Execute() {
        System.out.println("Execute: plugin maven");
        return true;
    }

    public boolean PostExecute() {
        System.out.println("PostExecute: plugin maven");
        return true;
    }
}
