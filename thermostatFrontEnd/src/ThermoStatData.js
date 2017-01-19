import * as React from 'react';
import axios from 'axios';

var axios_instance;
import './ThermostatStyle.css';

class ThermostatData extends React.Component {

    constructor(props) {
        super(props);
        this.state = {monitoringData: []};
        axios_instance = axios.create({
            baseURL: 'http://192.168.1.10:8080'
        });
    }

    componentDidMount() {
        axios_instance('/thermostat/realtime', {
            timeout: 20000,
            method: 'get',
            responseType: 'json'
        }).then((response) => {
            this.setState({
                monitoringData: response.data
            });
        });
    }

    componentWillMount() {

    }

    render() {
        return (
            <div>
                <div>
                    <table>
                        <thead>
                            <tr>
                                <th>Create Time</th>
                                <th>Temp</th>
                                <th>Fan On</th>
                                <th>AC on</th>
                                <th>Heat On</th>
                                <th>Aux Heat On</th>
                                <th>Raw Data</th>
                            </tr>
                        </thead>
                        <tbody>
                            { this.state.monitoringData.length > 0 &&
                              this.state.monitoringData.map((object) => {
                                 return <ParseData key={object.id} value={object}/>
                              })
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

class ParseData extends React.Component {
    render() {
        var object = this.props.value;
        return (<tr key={object.id}>
            <td>{this.formatDate(object.createTime)}</td>
            <td>{object.temperature}</td>
            <td>{object.fanOn.toString()}</td>
            <td>{object.acOn.toString()}</td>
            <td>{object.heatOn.toString()}</td>
            <td>{object.auxHeatOn.toString()}</td>
            <td>{object.rawData}</td>
       </tr> );
    }

    formatDate(timeStamp) {
        var date = new Date(timeStamp);
        var adjustedDate = date - (date.getTimezoneOffset() * 60 * 1000);
        return new Date(adjustedDate).toISOString();
    }
}

export default ThermostatData;