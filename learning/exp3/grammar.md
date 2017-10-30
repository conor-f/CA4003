`
Microsyntax
  SKIP -> [\x0d\x0a\x20\x09]
  NUM -> [0-9]+
  TOKEN -> [+*()] | NUM
Macrosyntax
  E -> id ":=" E | T ("+" T)*
  T -> F ("*" F)*
  F -> NUM | ID | "(" E ")"
`
