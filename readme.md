RUNNING
=======
- `mvn clean package`
- and now, either `run.sh`, or `run.bat`, according to your OS


WHAT IS IT?
===========
A sample project for ECJ (https://cs.gmu.edu/~eclab/projects/ecj/), to be used as a basis for your own project.
Just clone it, verify that it runs, and start making changes.

This project demonstrates Grammatical Evolution (GE). The examples that I've seen assume that we're using our Java
functions to interpret the generated code. Here we're taking other direction - and generate the code as a string, that
we pass to Python to interpret.

The idea is to generate code that prints 6 times "hello world".

GRAMMAR
-------
We'd like to use the following simple grammar:

    <start> -> <statement> | <statement> <start>
    <statement> -> <print> <string>
    <string> -> <word> | <word> <space> <word> | <word> <space> <word> <space> <word>
    <word> -> "hello" | "world" | "today"
    <space> -> " "

However, ECJ requires that "Each clause is a single GP node in Lisp s-expression form", which makes it a little 
cumbersome. To work around that, I've declared a `Cons` function, that just concatenates two strings. Thus,

    <start> -> <statement> | <statement> <start>

is written as:

    <start> ::= <statement> | (Cons <statement> <start>)

also note that each function must have a fixed number of parameters (unlike real LISP). Thus, for convenience, I've also 
declared a `Con3`, which is similar to `Cons`, but with 3 parameters.

Thus, the phrase `<word> <space> <word>` is written as `(Cons3 <word> <space> <word>)`.ce> <word>).

Another point is that each terminal node should be decalred as a function. Thus, we have `<space> ::= (Space)`

Finally, we could have 3 functions, for "hello", "world" and "today". But it's easier to use ERC mechanism. Thus:

    <word> ::= (WordERC)



