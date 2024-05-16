# GraalPy Integration

Java interoperability with Python using GraalPy.

### Build

```
graalpy -m venv .venv
.venv/bin/pip3 install requests
.venv/bin/python3.12 test.py 
```

```
mvn package
```

### Run

```
java -cp target/integration-toolkit-1.0-SNAPSHOT.jar com.mycompany.app.App
```