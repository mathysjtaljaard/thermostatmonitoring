/**
 * Created by mathysjtaljaard on 1/16/17.
 */
//https://github.com/gor181/react-chartjs-2
import * as React from 'react';
import data from '../public/thermostatData.json';
import {Line} from 'react-chartjs-2';

class Visualization extends React.Component {
    chartOptions = {
        responsive: true
    };

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

    render() {
        return (

            <Line data={this.generateLineChartData()} width={600} height={250} />
        );
    }

    generateLineChartData() {
        data.forEach((object) => {
            this.lineChartData.labels.push(this.getDateFormat(object.createTime));
            this.lineChartData.datasets[0].data.push(object.temperature);
        });

        this.lineChartData.datasets[0].label = 'Thermostat Data - ' + new Date(data[0].createTime).toDateString();
        return this.lineChartData;
    }

    getDateFormat(timestamp) {
        return new Date(timestamp).toISOString();
    }
}

export default Visualization;
