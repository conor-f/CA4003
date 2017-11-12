javacc Parser.jj
javac *.java

for i in $(ls tests/)
do
  echo $i
  java Parser "tests/$i"
  echo
done
