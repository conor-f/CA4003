Simple LL(1) grammar for an addition/multiplication language with addition
having lower precedence.

The LL(1) grammar describing this is:

`
Microsyntax
  SKIP -> [\x0d\x0a\x20\x09]
  NUM -> [0-9]+
  TOKEN -> [+*()] | NUM
Macrosyntax
  E -> T ("+" T)*
  T -> F ("*" F)*
  F -> NUM | "(" E ")"
`
