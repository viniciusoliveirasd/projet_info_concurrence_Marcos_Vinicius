# projet_info_concurrence_Marcos_Vinicius

INF-431 Project 

How many correct words can you type in five minutes ? Write a speed typing game to find
out. A player has five minutes to type a text via a user interface. The more correct words
it types in, the greater it score gets. There are two main requirements: (1) typed keys must
be displayed in real time and (2) the score must be updated while words are introduced.
The existence of every word must be checked and points should be added/subtracted for
every correct/incorrect word.
Use a countdown for starting the game. One game lasts for a fixed time interval, for
example five minutes. To check that a word is correct you can use a spell checker library
(a bit boring), e.g., Jazzy, or query a dictionary via network/web (recommended). The
number of points for correct/incorrect words is proportional to their length. For example,
if the player types “and” he/she gets three points and if he/she types ”nda” the player gets
minus three points.
Figure 1 shows a very simple user interface. The real-time requirement for key display
means that the interface must be programmed such that it never “freezes” the program is
always responsive to user interaction, no matter what it’s doing.


Moreover, let’s encourage creativity! Correct words and sentences, that appear in the
headlines of today’s journals don’t influence the score (you can use tweeter as an alternative
reference, e.g., the player that types a word that has a high frequency on tweeter today gets
only half of the points for that word). Feel free to make the game more fun by recognising
famous book titles, song titles, quotes from movies, and update the score according to your
appreciation for them. Inform the player whenever it is using phrases from newspapers,
quotes or book titles.
Here are some minimal display requirements for the interface: a component for introducing
the text, the time left until the game ends, a countdown for starting the game, the
current score, the best score, the incorrect words, the recognised words/sentences and their
sources.


