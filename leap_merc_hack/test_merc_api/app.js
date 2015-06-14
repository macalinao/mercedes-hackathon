import express from 'express';
import request from 'superagent-bluebird-promise';

let app = express();

// GhettoDB
let data = [];
data.pushSafe = function(e) {
  this.push(e);
  if (this.length > 1000) {
    this.shift();
  }
};

setInterval(function() {
  request.get('http://172.31.99.2/vehicle').then((res) => {
    data.pushSafe(res);
  });
}, 100);

setInterval(function() {
  console.log('Data is of length ' + data.length);
}, 4000);

app.use(express.static(__dirname + '/public'));

app.get('/data', (req, res) => {
  res.json(data);
});

let port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Listening on ${port}`);
});
