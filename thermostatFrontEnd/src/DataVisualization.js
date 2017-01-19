/**
 * Created by mathysjtaljaard on 1/16/17.
 */
//https://github.com/gor181/react-chartjs-2
//https://github.com/Hacker0x01/react-datepicker/
import * as React from 'react';
import {Line} from 'react-chartjs-2';
import DatePicker from 'react-datepicker';
import moment from 'moment';
import {retrieveGivenDaysData} from './services/data_retrieval_service';

class Visualization extends React.Component {

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

    constructor() {
        super();
        this.state = {
            monitoringData: [],
            startDate: moment(),
            endDate: moment()
        };
    }

    componentDidMount() {
        retrieveGivenDaysData(this.state.startDate, this.state.endDate, (err, data) => {
            console.log(data);
            var dataToUpdate = {
                monitoringData: data
            };
            this.setNewState(err, dataToUpdate);
        });
    }

    componentDidUpdate() {
        console.log('componentDidUpdate');
    }

    componentWillUnmount() {
        console.log('componentWillUnmount');
    }
    render() {
        var data = null;
        console.log('Rendering');
        if (this.state.monitoringData.length > 0) {
            console.log('have data');
            data = <Line data={this.generateLineChartData()} width={600} height={250} />
        } else {
            console.log('have no data');
            data = <h1>No Data To Display</h1>
        }

        return (
            <div>
                <DatePicker
                        selected={this.state.startDate}
                        selectsStart  startDate={this.state.startDate}
                        endDate={this.state.endDate}
                        onChange={(date) => {this.calculateDates(true, date)}} />
                <DatePicker
                        selected={this.state.endDate}
                        selectsEnd  startDate={this.state.startDate}
                        endDate={this.state.endDate}
                        onChange={(date) => {this.calculateDates(false, date)}} />
                <div>
                    {data}
                </div>
            </div>
        );
    }

    calculateDates(isStartDateChange, newDate) {
        var startDate = this.state.startDate;
        var endDate = this.state.endDate;

        if (isStartDateChange) {
            startDate = newDate;
            if (endDate.isBefore(newDate)) {
                endDate = newDate;
            }
        } else {
            endDate = newDate;
            if (startDate.isAfter(newDate)) {
                startDate = newDate;
            }
        }

        retrieveGivenDaysData(startDate, endDate, (err, data) => {
            console.log('callback called');
            var dataToUpdate = {
                startDate: startDate,
                endDate: endDate,
                monitoringData: data
            };
            this.setNewState(err, dataToUpdate);
        });
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

    setNewState(err, data) {

        if (err) {
            console.log(err);
        } else {
            console.log('setNewState', data);
            this.setState(data);
        }
    }
}

export default Visualization;
