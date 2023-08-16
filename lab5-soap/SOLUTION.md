# Solution

Note that you **MUST** user the `1.8` JDK version to compile and run this project because of the `javax.xml.ws` package. 

## Generate the WSDL files

1. Find the `wsimport` binary in your JDK installation folder. For example: 

```bash
cd ~/.jdks/corretto-1.8.0_382/bin
```

2. Run the `wsimport` command to generate the WSDL files:

```bash
./wsimport -d {PATH TO YOUR OUTPUT DIRECTORY} {WSDL URL}
```

For example:

```bash
./wsimport -d ~/Documents/Projects/Java/soap/src/main/java/wsdl/ http://localhost:8080/songs?wsdl
```