# DB_SQLite

Basic app_by using SQLite

<br>
I have used Stetho in this app to see all the data base tables in Chrome: <br>
http://facebook.github.io/stetho/
<br>


 // Gradle dependency on Stetho 
  dependencies { 
    compile 'com.facebook.stetho:stetho:1.3.1' 
  }


<br>
// in oCeate method add: <br>
    Stetho.initializeWithDefaults(this);
<br>

// make sure imported : Stetho
<br>
lunch :    chrome://inspect
