# Java Plugin Architecture 

### Build

```
make build
```

### Run

```
make up
```

### Write a Plugin ( Java Adapter )

This project accepts java plugins in the form of jar files. Examples are given in the plugins folder.

A plugin must follow the Design by Contract (DBC) pattern.

```
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
```