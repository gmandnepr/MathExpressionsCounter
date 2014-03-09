Scenario: sum

Given expression 2+3
When calculator evaluates and counts expression
Then the result is 5

Scenario: diff

Given expression 3-2
When calculator evaluates and counts expression
Then the result is 1

Scenario: expressions

Given expressions
|expression|result|
|2|2.0|
|1e6|1000000.0|
|(2)|2.0|
|((((2))))|2.0|
|+2|2.0|
|+(2)|2.0|
|-2|-2.0|
|-(2)|-2.0|
|-(-2)|2.0|
|2+2|4.0|
|+2+2|4.0|
|-2-2|-4.0|
|0-2|-2.0|
|0-2-2|-4.0|
|-(2+2)|-4.0|
|-(2+2)+2|-2.0|
|+(2+2)|4.0|
|2+2+2|6.0|
|sum(2;2)|4.0|
|-sum(2;2)|-4.0|
|(2+2)|4.0|
|(2+2)+2|6.0|
|2+(2+2)|6.0|
|sum(2;sum(2;2))|6.0|
|2+sum(2;2)|6.0|
|sum(2;2)+2|6.0|
|sum(2;2+2)|6.0|
|sum(2+2;2)|6.0|
|2*2+2|6.0|
|2+2*2|6.0|
|2+2*2+2|8.0|
|2*2+2*2|8.0|
|2*3*4|24.0|
|24/3/4|2.0|
|2+3*4^5|3074.0|
|(2+3*4)^5|537824.0|
|(sum(1;1))^5|32.0|
|(sum(1;3))!|24.0|
|(5)!|120.0|
|((3)!)!|720.0|
When calculator evaluates and counts expressions
Then the results are correct


