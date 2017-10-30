javacc Parser.jj
javac *.java

for i in $(ls tests/)
do
  java Parser "tests/$i"
done
