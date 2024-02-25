# GWS-SWE-CC4
Take home assignment for SWE Intern (GDS CC4.0)

## Features and Design Choices
### References
Build-related files like checkstyle and other configuration files were referenced from https://se-education.org/. 
### Use of `HashMap` to store payment mappings
Multiple entries with the same name are now allowed, which adds to the payer's total amount paid in the logic component.
This is to facilitate the flow of a normal trip with friends, where multiple transactions are made by the same people throughout the trip. 
### Allowing negative payment values
This is to account for situations where someone in the group has a windfall that they want to share with the entire group. 
>For example, the ongoing Taiwan lucky draw event where a member of the group may win NT$5000 that they intend to share with the rest of the group.
> The representation for such an input is:
> 
> ```Ali,-5000```

