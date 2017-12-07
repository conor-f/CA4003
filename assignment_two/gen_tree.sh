jjtree Parser.jjt
echo "Got the jj file :)"
javacc Parser.jj
echo "Compiled the jj file :) :)"
javac *.java

#java Parser "tests/num_test.ccl"
java Parser "tests/add.ccl"
#for i in $(ls tests/ | head -n 1)
#do
#  echo $i
#  java Parser "tests/$i"
#  echo
#done
