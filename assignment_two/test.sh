jjtree Parser.jjt
echo "Got the jj file :)"
javacc Parser.jj
echo "Compiled the jj file :) :)"
javac *.java

java Parser $1
