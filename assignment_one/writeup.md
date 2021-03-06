## CA4003 Assignment One - Conor Flynn ##

### Introduction ###
&nbsp;&nbsp;I found this assignment pretty difficult and ended up taking multiple different paths before deciding on this approach. All of the different ways I tried to make the grammar LL(1) are in different branches on the Github repository for the assignment: https://github.com/conor-f/CA4003

&nbsp;&nbsp;The real sticking point were the choice conflicts that appeared after removing the left recursion from expression and fragment. My final solution is not that great as it limits the language to only allow a simple expression in condition. The language loses very little functionality this way but is still slightly different from the original grammar. The place where this change was is clearly commented in the Parser.jj file at line 266.

&nbsp;&nbsp;I had the the clearest view of what I was doing and what edits I needed to make when I was working it out on paper, I include pictures of my workings at the end of the document for reference.
  
### Initial Steps ###
&nbsp;&nbsp;The first thing I did was set up this repository and write out the test cases included in the PDF. I then made a bash script which would run my parser against all these test cases. This helped me greatly throughout the assignment. I then created all the tokens and solved the basic issues like making the language case insensitive and allowing nested comments. It was then just a matter of writing out all the production rules as they were described in the grammar and seeing what problems arose.
  
### First Issues ###
&nbsp;&nbsp;Doing this presented me with two left-recursion errors. One direct (condition -> condition) and one indirect (expression -> fragment -> expression). I solved the direct left recursion easily with the help of the notes and then made the indirect left recursion direct and solved that. Removing the left recursion between expression and fragment lead to me creating fragment_prime which also had issues.

&nbsp;&nbsp;The issue with fragment_prime was that it presented a choice conflict from two possible rules leading to epsilon. I noticed that this would not be the case if I split fragment_prime into two different rules - fragment_prime_bool and fragment_prime_num. This approach looked like it would work until I realized that fragment_prime was referenced in the general fragment rule where it was preceded by expression or a function call - neither of which we can know the type of at this stage of the compilation process, so this approach wasn't going to work.
  
### Redo ###
&nbsp;&nbsp;I decided to start from scratch again, before I had removed any left recursion. I wanted to try squash the production rules for expression and fragment into one rule, and after doing this the indirect left recursion solved itself. I then removed the recursion in condition as before and got to the stage of choice conflicts. Some of these were easily removed with left factoring, but there was one final one I couldn't remove - it was in condition with a choice conflict on the <LB> token. The <LB> token could come from condition or expression and when I tried to left factor it I was just presented with the same choice conflict one level deeper. It was clear that repeating this process wasn't going to solve the conflict, so I decided to impose a limit on the language - only allowing a "simple" expression be part of a condition. This allowed me to left factor and not get any more issues so the grammar was then LL(1) and it was a good day.
  
### Conclusion ###
&nbsp;&nbsp;The issue I had could have been solved by a LOOKAHEAD of 3, but it could also have been solved by using a syntactic lookahead which is probably what I would have done if I was doing this independently of the condition that the grammar had to be strictly LL(1). I sometimes found that forcing it to be LL(1) with the choice conflicts made the grammar less readable to me and I think it would be an interesting project to write a Prolog script to take a grammar in EBNF form and return a LL(1) equivalent if one exists. It could be used to keep the readability of a grammar allowing lookaheads while still getting the memory improvements of having a LL(1) parser.


### Images ###

![](images/IMG_20171112_210642.jpg)
![](images/IMG_20171112_210734.jpg)
![](images/IMG_20171112_210757.jpg)
![](images/IMG_20171112_210654_2.jpg)
![](images/IMG_20171112_210741.jpg)
![](images/IMG_20171112_210813.jpg)
![](images/IMG_20171112_210719_2.jpg)
![](images/IMG_20171112_210752.jpg)
![](images/IMG_20171112_210819.jpg)
