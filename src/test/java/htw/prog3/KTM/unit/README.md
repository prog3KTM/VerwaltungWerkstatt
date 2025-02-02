# Test Naming Conventions

Test naming is important for teams working on long-term projects, just like any other code style convention. By applying a code convention in tests, you ensure that each test name is readable, understandable, and follows a well-known naming pattern for everyone on the project.

> "Proper names are poetry in the raw. Like all poetry they are untranslatable."  
> — W.H. Auden

## Why Do You Need a Naming Convention?
Before choosing a naming convention, you should first determine why you actually need it and what its purpose is. A consistent test naming convention improves:
- Readability
- Maintainability
- Team collaboration
- Test coverage clarity

## Recommendations for Test Naming
To maintain consistency and clarity in your tests, consider the following recommendations:

1. **Test name should express a specific requirement**
2. **Test name could include the expected input or state and the expected result for that input or state**
3. **Test name should be presented as a statement or fact of life that expresses workflows and outputs**
4. **Test name could include the name of the tested method or class**

By following these guidelines, your test names will remain clear, structured, and easy to understand, making them an asset rather than an obstacle in software development.

https://medium.com/@stefanovskyi/unit-test-naming-conventions-dd9208eadbea

We prefer this Naming Convention:
> MethodName_StateUnderTest_ExpectedBehavior

cons: should be renamed if method change name

example: isAdult_AgeLessThan18_False