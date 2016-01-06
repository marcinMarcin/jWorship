jWorship
========
Worship software used by Calvary community in Bratislava, Slovakia.

http://www.calvary.sk/text.php?text=jworship

## Building and running

```
mvn install
cd target
java -jar worship-1.0-SNAPSHOT.jar
```
or if you see an error message like "Invalid or corrupt jarfile" use:
```
mvn install
cd target
java -cp worship-1.0-SNAPSHOT.jar sk.calvary.worship.App
```
