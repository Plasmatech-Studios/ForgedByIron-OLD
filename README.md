# ForgedByIron
Forged By Iron Mobile Application and Web Service

Data needs to be loaded in this order:

Workout needs a createdBy/Owner, why exercise?
Workout needs a name/laebl

Exercise List? How is this being handles to not get duplicate UniqueIDs
If (exercise !exist : generate with premade UniqueID? Will this cause issues later?)

User -> UniqueID only -> Then Data?
Workout - > userID, workoutID -> Then Data
Exercise -> createdByID, workoutID, exerciseID -> Then Data
Set -> -> exerciseID, setID -> Then data

Handle Summary in Unique ID



public static String strSeparator = "__,__";
public static String convertArrayToString(String[] array){
    String str = "";
    for (int i = 0;i<array.length; i++) {
        str = str+array[i];
        // Do not append comma at the end of last element
        if(i<array.length-1){
            str = str+strSeparator;
        }
    }
    return str;
}
public static String[] convertStringToArray(String str){
    String[] arr = str.split(strSeparator);
    return arr;
}

npm install sqlite3
npm install body-parser