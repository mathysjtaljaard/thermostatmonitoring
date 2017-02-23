let SerialPort = require('serialport');
let request = require('request');
let moment = require('moment');
let os = require('os');

let port = null;
SerialPort.list((err, ports) => {
  for (let port of ports ) {
    if (port.pnpId && port.pnpId.toString().indexOf('FT232R_USB_UART_A602QRJ5') > -1) {
      initializePort(port.comName);
      break;
    }
  }
});

function initializePort(comName) {
  try {
    port = new SerialPort(comName, {
      parser: SerialPort.parsers.readline('\n'),
      baudRate: 115200
    });

    port.on('open', () => {
      console.log('port opened');
    }).on('error', function(err) {
      console.log('Error: ', err.message);
    }).on('data', (data) => {
      parseData(data);
    });
  }
  catch (ex) {
    console.error(ex);
  }

}

function parseData(data) {
  data = data.replace('\\r', '');
  let tempArray = data.split('|');
  if (tempArray.length === 5) {
    let finalDataArray = [];

    for (let v of tempArray) {
      v = v.trim();
      v = v.replace('\\r', '');
      finalDataArray.push(v);
    }

    let dataToSend = {
      createTimeISO: moment().utc(),
      fanOn: finalDataArray[0],
      heatOn: finalDataArray[1],
      acOn: finalDataArray[2],
      auxHeatOn: finalDataArray[3],
      temperature: finalDataArray[4]
    }
    postData(dataToSend);
  }
}

function postData(dataToSend) {
  request({
    method: 'POST',
    uri: 'http://localhost:8080/thermostat/data',
    headers: [
      {
        name: 'content-type',
        value: 'application/json; charset=utf-8'
      }
    ],
    body: JSON.stringify(dataToSend)
  }, (err, response) => {
    if (err) {
      console.log(err);
    }
  });
}
