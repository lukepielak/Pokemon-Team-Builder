# My Project Proposal

## As part of the CPSC 210 curriculum, I will be designing my own application in Java!

***What will the application do?***
- My application will be a "Pokemon Team Builder".
- Users will be able to choose Pokemon, their abilities, moves, and other specifics, to include on a team of up to 6 Pokemon.
- Users will be able to save an arbitrary amount of these Pokemon teams.

***Who will use it?***
- Anyone who is enthusiastic about Pokemon should enjoy my application and find it useful to plan-out different teams and experiment with unique combinations of moves and abilities. 

***Why is this project of interest to you?***
- I've been playing Pokemon games my whole life and have used applications in the past that have inspired the concept for my project.
- From my experience, existing "Pokemon team-building" applications have been lacking in certain areas.
- I am motivated to pursue this idea because I would love to eventually develop a version of this application that includes all the features I have always wanted.

## User Stories
- As a user, I want to be able to create a Pokemon team and add it to an arbitrarily sized list of Pokemon teams.
- As a user, I want to be able to choose a Pokemon to include on my team from a list of Pokemon.
- As a user, I want to be able to add up to 6 Pokemon to each of my teams.
- As a user, I want to be able to give a unique name to every team I make.
- As a user, I want to be able to select a team and view the Pokemon I have in that team.
- As a user, I want to be able to navigate a menu that allows me to view teams, make a team, or quit the application.
- As a user, I want to have the option to save and overwrite previously saved data before quitting. 
- As a user, I want to be able to load a save file after having closed the application and continue from where I left off.
- As a user, I want to see the teams I have created on the menu page.
- As a user, I want Pokemon to display a color corresponding to their type.
- As a user, I want to be able to delete the teams I have created.

## Instructions for Grader
***The "add multiple Xs to a Y" component is satisfied by adding Pokemon teams to a list of teams.***
1. Make a new team by clicking "Make new team".
2. Enter the name of the team you want to create.
3. Choose pokemon with the drop down menus, they are sorted by type.
4. Click each "Add" button to add the corresponding pokemon on its left. (maximum of 6 per team)
5. Click "OK" when you are finished, repeat steps to add more teams.

***There are two additional actions you can perform with the teams you created.***
1. Click "View teams" and choose a team from the drop down menu to see a more detailed look at one of your teams.
2. Click "Remove team" and choose a team from the drop down menu to delete the team.

***Certain interactions will prompt a pop-up box, each displays a different image of a pokemon. This satisfies the visual component.***
***The actions that prompt pop-up boxes are as follows:***
1. Try to make a team with no name.
2. Try to make a team with the same name as a team you have already created.
3. Add a Pokemon to a team.
4. Click "Cancel" while you are creating a new team.
5. Try to make a team with more than 6 Pokemon.
6. Delete a team.
7. Save data.
8. Load data.

***The saving and loading component is satisfied by a "Save" and a "Load" button on the menu.***
1. Save by clicking the "Save" button.
2. Load by clicking the "Load" button.

## Phase 4: Task 2
**Sample of event log printed to console:**
<br>
<br> Thu Aug 10 16:10:59 PDT 2023
<br> Team named Normal Team was added to the user's teams.
<br>
<br> Thu Aug 10 16:11:01 PDT 2023
<br> Pidgey was added to a team.
<br>
<br> Thu Aug 10 16:11:04 PDT 2023
<br> Pidgeotto was added to a team.
<br>
<br> Thu Aug 10 16:11:07 PDT 2023
<br> Pidgeot was added to a team.
<br>
<br> Thu Aug 10 16:11:09 PDT 2023
<br> Rattata was added to a team.
<br>
<br> Thu Aug 10 16:11:12 PDT 2023
<br> Raticate was added to a team.
<br>
<br> Thu Aug 10 16:11:15 PDT 2023
<br> Spearow was added to a team.
<br>
<br> Thu Aug 10 16:12:02 PDT 2023
<br> A team named Normal Team was removed from user's teams.

## Phase 4: Task 3
***How would I refactor my project if I had more time?***
<br>
One way I could improve the structure and readability of my code 
is by creating an abstract class to capture the shared behaviour between the "modal"
classes in my program. For instance, as many of the popup windows in my program share similar dimensions and buttons,
it would be benififcial to instead call an abstract method to implement this behaviour rather than repeating the 
same code in multiple places.

Furthermore, the color hashmap I am currently using to map Pokemon types to colors is initialized in "DisplayPokemonTeamModal",
although the same colors are used in another class, "ChoosePokemonModal", and may very well be used in new classes I decide to 
add in the future. If I initialized the hashmap of types to colors in a superclass, I could easily access it from any modal
in case I decide to add color to any other places in program. 

