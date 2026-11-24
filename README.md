#cpsc_459_project_03_blockchain

## macOS build commands

The `grading-2/README` uses semicolons, which are for Windows. macOS uses
colons (`:`) in Java classpaths.

Compile the starter sources:

```sh
cd "starter code-2"
javac -cp "rsa.jar:." *.java
```

Compile only `Transaction.java` and the class it depends on:

```sh
cd "starter code-2"
javac -cp "rsa.jar:." Transaction.java
```

Compile and run the grading test (after implementing `BlockChain.java`):

```sh
cd grading-2
javac -cp "blockChainGrader.jar:scroogeCoinGrader.jar:algs4.jar:rsa.jar:../starter code-2:." \
  -sourcepath "../starter code-2" DropboxTestBlockChain.java
java -cp "blockChainGrader.jar:scroogeCoinGrader.jar:algs4.jar:rsa.jar:../starter code-2:." \
  DropboxTestBlockChain
```
