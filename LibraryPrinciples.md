

1. For getXOrNull() : it will return the X with all the default considerations in function itself. it will never give any error since its enclosed in try catch
2. For getXOrError(): it won't consider any defaults and all the dependencies are needed to be provided by caller

3. For network, library supports all kinds of interceptors and convertors but gives first class 
   support to stetho/moshi and gson

gson/moshi interoperability
parcelize,
refactoring



waiting:
- hilt base files
- dependency inside dependency