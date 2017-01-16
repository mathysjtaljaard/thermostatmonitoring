/**
 * Created by mathysjtaljaard on 1/16/17.
 */
import * as React from 'react';
import data from '../public/thermostatData.json';
import style from './ThermostatStyle.css';

class ThermostatData extends React.Component {

    render() {
        console.log(data);
        return (
            <div>
                <p>Thermostat Data</p>
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
                            </tr>
                        </thead>
                        <tbody>
                            {
                                data.map((object) => {
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
        console.log('In parseData -> ',this.props.value);
        var object = this.props.value;
        return (<tr key={object.id}>
            <td>{this.formatDate(object.createTime)}</td>
            <td>{object.temperature}</td>
            <td>{object.fanOn.toString()}</td>
            <td>{object.acOn.toString()}</td>
            <td>{object.heatOn.toString()}</td>
            <td>{object.auxHeatOn.toString()}</td>
       </tr> );
    }

    formatDate(timeStamp) {
        console.log('received value', timeStamp);
        var date = new Date(timeStamp);
        return date.toISOString();
    }
}


export default ThermostatData;
