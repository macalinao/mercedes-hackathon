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

app.use(express.static(__dirname + '/public'));

let port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Listening on ${port}`);
});
