# Streaming-Platform

## Structure

```
.
├── Main.java
├── Test.java
├── actions
│   ├── Action.java
│   ├── changepage
│   │   ├── ChangePageLogOut.java
│   │   ├── ChangePageLogin.java
│   │   ├── ChangePageMovies.java
│   │   ├── ChangePageRegister.java
│   │   ├── ChangePageSeeDetails.java
│   │   └── ChangePageUpgrades.java
│   ├── database
│   │   ├── AddDataBase.java
│   │   └── DeleteDataBase.java
│   ├── onpage
│   │   ├── BuyPremiumAccount.java
│   │   ├── BuyTokens.java
│   │   ├── Filter.java
│   │   ├── Like.java
│   │   ├── Login.java
│   │   ├── Purchase.java
│   │   ├── RateMovie.java
│   │   ├── Register.java
│   │   ├── Search.java
│   │   ├── Subscribe.java
│   │   └── Watch.java
│   └── other
│       ├── Back.java
│       └── Recommend.java
├── checker
│   ├── CheckStyleConstants.java
│   ├── Checker.java
│   ├── CheckerConstants.java
│   ├── Checkstyle.java
│   ├── Constants.java
│   ├── checkstyle-8.36.2-all.jar
│   ├── checkstyle.sh
│   └── poo_checks.xml
├── database
│   ├── DataBase.java
│   ├── NotifyAdded.java
│   └── NotifyDeleted.java
├── entities
│   ├── Credentials.java
│   ├── Movie.java
│   ├── Notification.java
│   └── User.java
├── fileio
│   ├── ActionInput.java
│   ├── Input.java
│   ├── MovieInput.java
│   ├── Output.java
│   └── UserInput.java
├── main
│   ├── Accessibility.java
│   ├── Helpers.java
│   ├── State.java
│   └── accessibility.json
└── proiect1.iml
```

As you may see in the provided diagram, the classes were organised 
in the following packages:

1. `main` - general purpose classes

2. `fileio` - input and output classes, used for interacting
with the data provided and requested

3. `entities` - real objects, such as `user` and `movie`

4. `checker` - provided checkstyle checker

5. `actions` - desired functionalities, each representing a class
   1. `changepage` - navigation from one page to another
   2. `onpage` - actions such as `filter`, `search`, `register`, presented on a page
   3. `database` - actions regarding interaction with the movies database
   4. `other` - remaining actions, such as `recommend` and `back`
   5. `database` - classes used for storing the database and providing the interaction
   between users and new launched or deleted movies

## Design Patterns

For this assignment, 4 Design Patterns were used:

1. **Singleton** for `State` class since at each moment we are in a certain State.

2. **Factory** for `Actions` in order to construct the necessary actions
and operate with them naturally

3. **Observer** was used for notifying the users when operations on the database are done:
   1. `Database` is the `Observable` class - it notifies other classes to take the appropriate measures
   2. `NotifyAdded` and `NotifyDeleted` are the `Observers` - they send the notifications

4. **Builder** was used for `Output` class in order to have a flexible way of
creating printed messages

## Flow

For each test, the algorithm is sequentially followed:

1. Firstly, the input from the passed file is loaded into memory. For this purpose,
we have created the classes in the `fileio` package, that follow the given structure

2. Then, the transformation from the input-designed classes into
the general-use classes arises (for example, from a list of `MovieInput` objects,
we inject our `DataBase` with `Movie` instances)

3. Since navigation between pages is tested in this assignment, an adjacency
matrix is loaded into memory as well (from the file `accessibility.json`). This is an
efficient way to verify the correctness of various page changes as edges in a graph

4. The entire information is loaded into the `State` object: 
   1. `currentUser`
   2. `currentPage`
   3. `visibleMovies`
   4. `database` access, as well as a reference to the `accesibility` matrix
   5. `previousPages` stack, which is essential for `back` operations
   
   At each step, the entire configuration may be described by this state - 
   each action may be interpreted as a transition in the state-graph 
   The database stores both the `users`, and the `movies` and allows
   (with notification enabled using the **observer** design pattern)
   inserting and deleting movies

5. The next step is to iterate through the set of actions and to `apply` them (the 
   output from each action will be collected in a variable that will be printed as result). 
   This elegant solution was implemented using a common abstract class `Action` that 
   provides the abstract method `apply`. This abstract class also provides a `factory`
   method for creating the desired `Action` subclasses instances. The `actions` implemented
   are present in the structure `provided` above

6. The final part is to provide the movie recommendation for the premium connected user
   (if it is the case)

###### Copyright: Vlad Negoiță 321CAb vlad1negoita@gmail.com
