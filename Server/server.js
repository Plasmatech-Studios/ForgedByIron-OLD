const { readFileSync, writeFileSync } = require('fs');
const path = require('path');
const fileLoc = path.join(__dirname, 'count.txt');
const express = require('express');

//const cors = require('cors'); // Do i need this?

const app = express();
//app.use(cors()); // Do i need this?

app.get('/', (req, res) => {
    
    const data = readFileSync(fileLoc, 'utf8');
    const count = parseInt(data) + 1;
    console.log('\n',new Date().toLocaleString("en-US", {timeZone: "Australia/Sydney"}),'\n New Connection! New count:', count);
    writeFileSync(fileLoc, count.toString());
    console.log(req.headers);
    // check if the req headers contain the header cmd
    if (req.headers.cmd) {
        console.log('cmd header found!');
    }
    if (req.headers['cmd'] == 'get') {
        console.log('Loading UID!', req.headers['uid']);
        console.log('Data Loaded', req.headers['data']);
        res.send('Test header found!');
    } else {
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
    }

});

app.get('/api', (req, res) => {
    console.log(req);
    res.send('Hi there!');
});
app.listen(5000, () => console.log('http://localhost:5000'));