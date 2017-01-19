/**
 * Created by mathysjtaljaard on 1/16/17.
 */
//https://github.com/gor181/react-chartjs-2
import * as React from 'react';
import {Line} from 'react-chartjs-2';
import axios from 'axios';

var axios_instance;

class VisualizationDay extends React.Component {

    lineChartData = {
        labels: [],
        datasets: [
            {
                label: 'Thermostat Data',
                fill: false,
                lineTension: 0.1,
                backgroundColor: 'rgba(75,192,192,0.4)',
                borderColor: 'rgba(75,192,192,1)',
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: 'rgba(75,192,192,1)',
                pointBackgroundColor: '#fff',
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: 'rgba(75,192,192,1)',
                pointHoverBorderColor: 'rgba(220,220,220,1)',
                pointHoverBorderWidth: 2,
                pointRadius: 1,
                pointHitRadius: 10,
                data: []
            }
        ]};

    constructor(props) {
        super(props);
        this.state = {monitoringData: []};
        axios_instance = axios.create({
            baseURL: 'http://192.168.1.10:8080'
        });
    }

    componentDidMount() {
        let date = new Date();
        let stringDate = date.getFullYear().toString() + '-'+(date.getMonth().valueOf() +1)+'-'+date.getDate().toString();
        console.log(stringDate);
        axios_instance('/thermostat/day/'+stringDate, {
            timeout: 20000,
            method: 'get',
            responseType: 'json'
        }).then((response) => {
            this.setState({
                monitoringData: response.data
            });
        });
    }

    render() {
        if (this.state.monitoringData.length > 0) {
            return  <Line data={this.generateLineChartData()} width={600} height={250} />;
        } else {
            return <Line data={this.lineChartData}  width={600} height={250} />;
        }
    }

    generateLineChartData() {
        this.state.monitoringData.forEach((object) => {
            this.lineChartData.labels.push(this.getDateFormat(object.createTime));
            this.lineChartData.datasets[0].data.push(object.temperature);
        });

        this.lineChartData.datasets[0].label = 'Thermostat Data - ' + new Date(this.state.monitoringData[0].createTime).toDateString();
        return this.lineChartData;
    }

    getDateFormat(timestamp) {
        var date = new Date(timestamp);
        var adjustedDate = date - (date.getTimezoneOffset() * 60 * 1000);
        return new Date(adjustedDate).toISOString();
    }
}

export default VisualizationDay;
