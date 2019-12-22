# lottery-simulation
### It is a program that simulates the draw of 6 unique numbers in the range from **1 and 49** and compares them with the numbers you enter.
![lotto-simulator](https://user-images.githubusercontent.com/26818304/52859447-080efc80-312d-11e9-90e1-3d616da76deb.png)
 
A program written using JavaFX without using FXML, graphically simple. 

* you can enter counts in boxes from the keyboard or using a spinner. 
* the program is resistant to entering characters other than numbers. 
* giving the value to more than 49, the program will automatically set 49, giving a value less than 1 program set to 1. 
* you can use the random field settings with values (then you can also modify them). 
* the program automatically draws and fills the fields with new values after each draw and finding the searched 6 digits. 
* you can not provide two of the same values in the draw, the program will display an information message with the message that you have provided duplicate values.

### What information do you get?

The _Show details_ button shows more details when it comes to the draw process. After the first draw, the button will be active and clickable.
For example, for the given numbers: `4, 11, 13, 30, 43, 49` the program hit these values for 2 866 948 attempts in my case.
In the details are the information for which the first attempt has been hit the value and what number from the entered by you:
```
Found 1 values. The values: [13] hit after the 1 attempt
Found 2 values. The values: [11, 13] hit after the 7 attempt
Found 3 values. The values: [49, 13, 30] hit after the 45 attempt
Found 4 values. The values: [49, 11, 13, 30] hit after the 3 187 attempt
Found 5 values. The values: [4, 43, 11, 13, 30] hit after the 43 228 attempt
Found 6 values. The values: [49, 4, 43, 11, 13, 30] hit after the 2 866 948 attempt
```

The next information in the details tells how many sets of numbers you gave in the length of 1, 2, 3, 4 and 5 were hit before the entire set of digits with the length was hit. For example:


`The number of occurrences hits for a set of three numbers: 50,410`

The values could be: `4, 11, 13` or / and `13, 49, 43` and / or `30, 4, 13` and so on.


`The number of occurrences hits for a set of four numbers: 2,752`

The values could be: `4, 11, 13, 30` or / and `13, 49, 43, 11` or / and `30, 4, 13, 49` and so on.

---
_Remember that depending on the power of your machine and luck in the draw, the draw process can take up to several seconds._

Example of full details"
```$xslt
---------------------
Results for numbers: [8, 17, 2, 21, 13, 14]
Found 1 values. The values: [14] hit after the 4 attempt
Found 2 values. The values: [17, 8] hit after the 11 attempt
Found 3 values. The values: [17, 2, 14] hit after the 178 attempt
Found 4 values. The values: [17, 2, 21, 13] hit after the 3 770 attempt
Found 5 values. The values: [17, 2, 21, 13, 14] hit after the 34 838 attempt
Found 6 values. The values: [17, 2, 21, 8, 13, 14] hit after the 52 494 109 attempt

The number of occurrences of hits for one number: 21 673 553
The number of occurrences of hits for a set of two numbers: 6 954 432
The number of occurrences of hits for a set of three numbers: 925 928
The number of occurrences of hits for a set of four numbers: 50 832
The number of occurrences of hits for a set of five numbers: 943
``` 

Have fun!
