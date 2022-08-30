const sqlite3 = require('sqlite3').verbose();

const { readFileSync, writeFileSync } = require('fs');
const path = require('path');
const saveFileLoc = path.join(__dirname, 'count.txt');
const dbLoc = path.join(__dirname, '/db/FBI.db');
const express = require('express');
const bodyParser = require("body-parser");

const router = express.Router();
const app = express();
app.use('/' , router);
app.use('/api' , router);
app.use('/FBI_AUTH' , router);

app.use(bodyParser.urlencoded({ extended: true }));

router.get('/', (req, res) => {
    const count = readFileSync(saveFileLoc, 'utf8');
    const newCount = parseInt(count) + 1;
    writeFileSync(saveFileLoc, newCount.toString());
    res.send(`        
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <title> Forged By Iron </title>
            </head>
            <body>
                <h1>Welcome to Forged By Iron</h1>
                <h2>This page has been viewed ${count} times!</h2>
            </body>
            </html>            
        `);

    console.log('get request', count, req.headers);
});


    //res.sendFile(path.join(__dirname, 'index.html'));

router.get('/api', (req, res) => {
    console.log('HCS get request', req.headers);
    res.sendFile(path.join(__dirname, 'HCS_Sign.html'));

});

// Used to log in Users to the FBI System, redirects to the main page the request is invalid
router.get('/FBI_AUTH', (req, res) => {
    console.log('FBI_AUTH get request', req.headers);
    // check if a header is present
    if (req.headers["x-fbi-username"] && req.headers["x-fbi-secretkey"]) { // Required headers
        console.log('FBI_AUTH get request', req.headers["x-fbi-username"], req.headers["x-fbi-secretkey"]);
        // Do something
        res.send('Welcome ' + req.headers["x-fbi-username"]);

    } else {
        console.log('Invalid request to FBI_AUTH');
        res.sendFile(path.join(__dirname, 'index.html'));
    }
});

// Used to get data from the FBI System, redirects to the main page the request is invalid
router.get('/FBI_GET', (req, res) => {
    console.log('FBI_GET get request', req.headers);
    // check if a header is present
    if (req.headers["x-fbi-command"] && req.headers["x-fbi-uniqueid"] && req.headers["x-fbi-username"] && req.headers["x-fbi-secretkey"]) { // Required headers
        console.log('FBI_GET get request command:', req.headers["x-fbi-command"], "requested object", req.headers["x-fbi-uniqueid"] , "from User: " + req.headers["x-fbi-username"]);
        // Do something
        res.send('Here is your data ' + req.headers["x-fbi-username"]);

    } else {
        console.log('Invalid request to FBI_GET');
        res.sendFile(path.join(__dirname, 'index.html'));
    }
});


// Currently only used for HCS
router.post('/api', (req, res) => {
    console.log('post request', req.body);
    const count = readFileSync(saveFileLoc, 'utf8');
    const newCount = parseInt(count) + 1;
    writeFileSync(saveFileLoc, newCount.toString());
    res.send(`Count: ${newCount}`);
});

app.listen(5000, () => console.log('http://localhost:5000'));



////////////////////////////////////////////////////////////////////
// DATEBASE CODE
////////////////////////////////////////////////////////////////////
// create a new database 

let db = new sqlite3.Database(dbLoc, (err) => {
  if (err) {
    return console.error('am i here', err.message);
  }
  console.log('Connected to the Forged By Iron SQlite database.');
});


// close the database connection
db.close((err) => {
  if (err) {
    return console.error(err.message);
  }
  console.log('Closed the database connection.');
});