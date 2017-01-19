/**
 * Created by mathysjt on 1/22/17.
 */
var axios = require('axios');
var config = require('../configuration/config.json');

var request = axios.create(config.axiosConfig);

function retrieveGivenDaysData(startDate, endDate, callback) {
    console.log('retrieveGivenDaysData=> ', startDate.toISOString());
    if (startDate.isValid && endDate.isValid) {
        var path = config.rangePath;
        path = path.replace('{startDate}', startDate.toISOString());
        path = path.replace('{endDate}', endDate.toISOString());
        console.log(path);
        requestData(path, callback);
    } else {
        callback("Error: Date was not valid");
    }
}

function requestData(path, callback) {
    request(path).then((response) => {
        callback(null, response.data);
    }).catch((ex) => {
        callback(ex);
    });
}

export {retrieveGivenDaysData};