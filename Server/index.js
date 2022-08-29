const http =  require('https');
const data = JSON.stringify({
  name:"John Doe",
  Job: "Content Writer"
});

const options = {
  hostname: 'fbi.sytes.net',
  path: '/api',
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  }
};

const req = http.request(options, res => {
  let body = '';
  console.log('Status Code:', res.statusCode)

  res.on('data', d => {
    body+=d;
  })

  res.on('end', () => {
    console.log("Body:", JSON.parse(body));
  })
})

req.write(data)
req.end()