# use-password-manager-service

PasswordGenerator class that create random password consisting of letters, numbers and symbols. 

### Example
```
  PasswordGenerator generator = new PasswordGenerator();
  noOfDigits = 2, 
  noOfCapitalLetters = 1, 
  noOfSpecialChar = 2, 
  lengthOfDesiredPassword = 12
  
  generator.generateRandomString(
    noOfDigits, noOfCapitalLetters, noOfSpecialChar, lengthOfDesiredPassword);
  
  random output : y@fdrKjpf1%0
 
```
