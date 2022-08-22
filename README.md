# ForgedByIron
Forged By Iron Mobile Application and Web Service

Thoughts:
Should all classes that have a uniqueID extend uniqueID?


Data needs to be loaded in this order:

Workout needs a createdBy/Owner, why exercise?

User -> UniqueID only -> Then Data?
Workout - > userID, workoutID -> Then Data
Exercise -> createdByID, workoutID, exerciseID -> Then Data
Set -> -> exerciseID, setID -> Then data